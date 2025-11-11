package com.mhyusuf.service;

import com.mhyusuf.dto.VariantDto;
import com.mhyusuf.entity.Item;
import com.mhyusuf.entity.Variant;
import com.mhyusuf.exception.BadRequestException;
import com.mhyusuf.exception.ResourceNotFoundException;
import com.mhyusuf.repository.ItemRepository;
import com.mhyusuf.repository.VariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class VariantService {

    private final VariantRepository variantRepository;
    private final ItemRepository itemRepository;

    /**
     * Retrieve all variants (can be filtered later if needed).
     */
    public List<VariantDto> getAllVariants() {
        return variantRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieve variant by ID.
     */
    public VariantDto getVariantById(UUID id) {
        Variant variant = variantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Variant not found with id: " + id));
        return toDto(variant);
    }

    /**
     * Create a new variant for a specific item.
     */
    public VariantDto createVariant(VariantDto dto) {
        if (dto.getItemId() == null) {
            throw new BadRequestException("Item ID must not be null when creating a variant");
        }

        Item item = itemRepository.findById(dto.getItemId())
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + dto.getItemId()));

        if (dto.getVariantName() == null || dto.getVariantName().trim().isEmpty()) {
            throw new BadRequestException("Variant name must not be empty");
        }

        if (variantRepository.findByItemAndVariantName(item, dto.getVariantName()).isPresent()) {
            throw new BadRequestException("Variant with the same name already exists for this item");
        }

        /* Validasi harga harus >= 0 menggunakan BigDecimal
         * compareTo(BigDecimal.ZERO):
         * > 0 → value lebih besar dari nol
         * == 0 → value sama dengan nol
         * < 0 → value lebih kecil dari nol
         */
        if (dto.getPrice() == null || dto.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException("Price must be greater than or equal to zero");
        }

        if (dto.getStock() != null && dto.getStock() < 0) {
            throw new BadRequestException("Stock must be zero or positive");
        }

        Variant variant = new Variant(
                item,
                dto.getVariantName(),
                dto.getColor(),
                dto.getSize(),
                dto.getPrice(),
                dto.getStock() != null ? dto.getStock() : 0
        );
        variant.setCreatedBy(dto.getCreatedBy());
        variant.setUpdatedBy(dto.getUpdatedBy());

        Variant saved = variantRepository.save(variant);
        return toDto(saved);
    }

    /**
     * Update variant details.
     */
    public VariantDto updateVariant(UUID id, VariantDto dto) {
        Variant variant = variantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Variant not found with id: " + id));

        if (dto.getVariantName() != null && !dto.getVariantName().trim().isEmpty()) {
            Optional<Variant> existingVariant = variantRepository.findByItemAndVariantName(variant.getItem(), dto.getVariantName());
            if (existingVariant.isPresent() && !existingVariant.get().getId().equals(id)) {
                throw new BadRequestException("Variant name already exists for this item");
            }
            variant.setVariantName(dto.getVariantName());
        }

        if (dto.getColor() != null) {
            variant.setColor(dto.getColor());
        }

        if (dto.getSize() != null) {
            variant.setSize(dto.getSize());
        }

        if (dto.getPrice() != null) {
            /* Validasi harga harus >= 0 menggunakan BigDecimal
             * compareTo(BigDecimal.ZERO):
             * > 0 → value lebih besar dari nol
             * == 0 → value sama dengan nol
             * < 0 → value lebih kecil dari nol
             */
            if (dto.getPrice().compareTo(BigDecimal.ZERO) < 0) {
                throw new BadRequestException("Price must not be negative");
            }
            variant.setPrice(dto.getPrice());
        }

        if (dto.getStock() != null) {
            if (dto.getStock() < 0) throw new BadRequestException("Stock must not be negative");
            variant.setStock(dto.getStock());
        }

        variant.setUpdatedAt(new Date());
        variant.setUpdatedBy(dto.getUpdatedBy());

        Variant updated = variantRepository.save(variant);
        return toDto(updated);
    }

    /**
     * Delete a variant by ID.
     */
    public void deleteVariant(UUID id) {
        if (!variantRepository.existsById(id)) {
            throw new ResourceNotFoundException("Variant not found with id: " + id);
        }
        variantRepository.deleteById(id);
    }

    /**
     * Helper method to convert Entity -> DTO.
     */
    private VariantDto toDto(Variant v) {
        return new VariantDto(
                v.getId(),
                v.getItem().getId(),
                v.getVariantName(),
                v.getColor(),
                v.getSize(),
                v.getPrice(),
                v.getStock(),
                v.getCreatedBy(),
                v.getUpdatedBy(),
                v.getCreatedAt(),
                v.getUpdatedAt()
        );
    }
}
