package com.mhyusuf.service;

import com.mhyusuf.dto.SaleDto;
import com.mhyusuf.dto.SaleItemDto;
import com.mhyusuf.entity.Sale;
import com.mhyusuf.entity.SaleItem;
import com.mhyusuf.entity.Variant;
import com.mhyusuf.exception.BadRequestException;
import com.mhyusuf.exception.ResourceNotFoundException;
import com.mhyusuf.repository.SaleRepository;
import com.mhyusuf.repository.SaleItemRepository;
import com.mhyusuf.repository.VariantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SalesService {

    private final SaleRepository saleRepository;
    private final SaleItemRepository saleItemRepository;
    private final VariantRepository variantRepository;
    private final StockService stockService;

    /**
     * Create a new sale transaction and automatically adjust stock.
     */
    public SaleDto createSale(SaleDto request) {
        log.info("Starting new sale transaction for customer: {}", request.getCustomerName());

        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new BadRequestException("Sale must contain at least one item.");
        }

        Sale sale = new Sale();
        sale.setCustomerName(request.getCustomerName());
        sale.setTotalAmount(BigDecimal.ZERO);

        BigDecimal total = BigDecimal.ZERO;

        // Process each sale item
        for (SaleItemDto itemDto : request.getItems()) {
            Variant variant = variantRepository.findById(itemDto.getVariantId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Variant not found with id: " + itemDto.getVariantId()));

            if (itemDto.getQuantity() == null || itemDto.getQuantity() <= 0) {
                throw new BadRequestException("Quantity must be greater than zero for variant: " + variant.getId());
            }

            // Check stock availability and decrease it
            stockService.decreaseStock(variant.getId(), itemDto.getQuantity());

            BigDecimal subtotal = variant.getPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity()));

            SaleItem saleItem = new SaleItem();
            saleItem.setVariant(variant);
            saleItem.setQuantity(itemDto.getQuantity());
            saleItem.setSubtotal(subtotal);
            sale.addItem(saleItem);

            total = total.add(subtotal);

            log.debug("Added variant [{}] x{} subtotal = {}", variant.getId(), itemDto.getQuantity(), subtotal);
        }

        sale.setTotalAmount(total);
        Sale savedSale = saleRepository.save(sale);

        log.info("Sale transaction completed successfully. Total amount: {}", total);

        return mapToDto(savedSale);
    }

    /**
     * Retrieve all sales.
     */
    @Transactional(readOnly = true)
    public List<SaleDto> getAllSales() {
        log.debug("Fetching all sales...");
        List<SaleDto> sales = saleRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        log.info("Total {} sales retrieved successfully.", sales.size());
        return sales;
    }

    /**
     * Retrieve a sale by ID.
     */
    @Transactional(readOnly = true)
    public SaleDto getSaleById(UUID saleId) {
        log.debug("Fetching sale details for ID: {}", saleId);
        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found with id: " + saleId));
        log.info("Sale [{}] retrieved successfully.", saleId);
        return mapToDto(sale);
    }


    /**
     * Map Sale entity to SaleDto.
     * @param sale Sale entity
     * @return SaleDto
     */
    private SaleDto mapToDto(Sale sale) {
        return SaleDto.builder()
                .id(sale.getId())
                .customerName(sale.getCustomerName())
                .totalAmount(sale.getTotalAmount())
                .createdAt(sale.getCreatedAt())
                .items(
                        sale.getItems()
                                .stream()
                                .map(this::mapToItemDto)
                                .collect(Collectors.toList())
                )
                .build();
    }

    public List<SaleDto> filterSales(LocalDateTime startDate, LocalDateTime endDate,
                                     String customerName, BigDecimal minTotal, BigDecimal maxTotal) {
        log.debug("Applying filters to sales query...");

        return saleRepository.findAll().stream()
                .filter(sale -> startDate == null || !sale.getCreatedAt().isBefore(startDate))
                .filter(sale -> endDate == null || !sale.getCreatedAt().isAfter(endDate))
                .filter(sale -> customerName == null ||
                        sale.getCustomerName().toLowerCase().contains(customerName.toLowerCase()))
                .filter(sale -> minTotal == null || sale.getTotalAmount().compareTo(minTotal) >= 0)
                .filter(sale -> maxTotal == null || sale.getTotalAmount().compareTo(maxTotal) <= 0)
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 🧩 Tambahkan method ini agar mapping entity ke DTO aman dan readable
    private SaleDto convertToDto(Sale sale) {
        if (sale == null) return null;

        SaleDto dto = new SaleDto();
        dto.setId(sale.getId());
        dto.setCustomerName(sale.getCustomerName());
        dto.setTotalAmount(sale.getTotalAmount());
        dto.setCreatedAt(sale.getCreatedAt());

        if (sale.getItems() != null) {
            List<SaleItemDto> itemDtos = sale.getItems().stream()
                    .map(this::convertToItemDto)
                    .collect(Collectors.toList());
            dto.setItems(itemDtos);
        }

        return dto;
    }

    // Helper method untuk mapping SaleItem → SaleItemDto
    private SaleItemDto convertToItemDto(SaleItem item) {
        if (item == null) return null;

        SaleItemDto dto = new SaleItemDto();
        dto.setId(item.getId());
        dto.setVariantId(item.getVariant().getId());
        dto.setVariantName(item.getVariant().getVariantName());
        dto.setQuantity(item.getQuantity());
        dto.setSubtotal(item.getSubtotal());
        return dto;
    }

    /**
     * Map SaleItem entity to SaleItemDto.
     * @param saleItem SaleItem entity
     * @return SaleItemDto
     */
    private SaleItemDto mapToItemDto(SaleItem saleItem) {
        return SaleItemDto.builder()
                .id(saleItem.getId())
                .variantId(saleItem.getVariant().getId())
                .variantName(saleItem.getVariant().getVariantName())
                .quantity(saleItem.getQuantity())
                .subtotal(saleItem.getSubtotal())
                .build();
    }


}
