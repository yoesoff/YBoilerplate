package com.mhyusuf.service;

import com.mhyusuf.dto.StockDto;
import com.mhyusuf.entity.Variant;
import com.mhyusuf.exception.BadRequestException;
import com.mhyusuf.exception.OutOfStockException;
import com.mhyusuf.exception.ResourceNotFoundException;
import com.mhyusuf.repository.StockRepository;
import com.mhyusuf.repository.VariantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class StockService {

    private final StockRepository stockRepository;
    private final VariantRepository variantRepository;

    /**
     * Retrieve current stock for a specific variant.
     */
    public Integer getStock(UUID variantId) {
        Variant variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new ResourceNotFoundException("Variant not found with id: " + variantId));
        return variant.getStock();
    }

    /**
     * Adjust stock to an absolute new value.
     * Example use: admin manually updates stock level.
     */
    public void adjustStock(StockDto dto) {
        if (dto.getStock() == null) {
            throw new BadRequestException("Stock value must not be null");
        }

        if (dto.getStock() < 0) {
            throw new BadRequestException("Stock value cannot be negative");
        }

        Variant variant = variantRepository.findById(dto.getVariantId())
                .orElseThrow(() -> new ResourceNotFoundException("Variant not found with id: " + dto.getVariantId()));

        variant.setStock(dto.getStock());
        variantRepository.save(variant);
    }

    /**
     * Decrease stock by a certain quantity (used for sales or checkout flow).
     */
    public void decreaseStock(UUID variantId, int quantity) {
        if (quantity <= 0) {
            throw new BadRequestException("Quantity to decrease must be greater than zero");
        }

        Variant variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new ResourceNotFoundException("Variant not found with id: " + variantId));

        Integer currentStock = variant.getStock();
        if (currentStock < quantity) {
            throw new OutOfStockException("Not enough stock for variant: " + variantId);
        }

        variant.setStock(currentStock - quantity);
        variantRepository.save(variant);
    }

    /**
     * Increase stock by a certain quantity (used for restocking or returns).
     */
    public void increaseStock(UUID variantId, int quantity) {
        if (quantity <= 0) {
            throw new BadRequestException("Quantity to increase must be greater than zero");
        }

        Variant variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new ResourceNotFoundException("Variant not found with id: " + variantId));

        int newStock = variant.getStock() + quantity;
        variant.setStock(newStock);
        variantRepository.save(variant);
    }
}
