package com.mhyusuf.controller.rest;

import com.mhyusuf.dto.StockDto;
import com.mhyusuf.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

/**
 * REST Controller for managing stock adjustments and lookups.
 * Provides clean and safe endpoints for modifying stock levels.
 */
@RestController
@RequestMapping("/api/stock")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    /**
     * Retrieve current stock for a specific variant.
     * Example: GET /api/stock/{variantId}
     */
    @GetMapping("/{variantId}")
    public ResponseEntity<Map<String, Object>> getStock(@PathVariable UUID variantId) {
        Integer stock = stockService.getStock(variantId);
        return ResponseEntity.ok(Map.of(
                "variantId", variantId,
                "stock", stock
        ));
    }

    /**
     * Adjust stock to a specific absolute value.
     * Example: PATCH /api/stock/adjust
     * Request Body:
     * {
     *   "variantId": "a12c34e5-67b8-90d1-23ef-4567890abcd1",
     *   "stock": 25
     * }
     */
    @PatchMapping("/adjust")
    public ResponseEntity<Map<String, Object>> adjustStock(@RequestBody StockDto dto) {
        stockService.adjustStock(dto);
        return ResponseEntity.ok(Map.of(
                "message", "Stock adjusted successfully",
                "variantId", dto.getVariantId(),
                "newStock", dto.getStock()
        ));
    }

    /**
     * Decrease stock (used for sales, orders, etc.).
     * Example: POST /api/stock/{variantId}/decrease?quantity=3
     */
    @PostMapping("/{variantId}/decrease")
    public ResponseEntity<Map<String, Object>> decreaseStock(
            @PathVariable UUID variantId,
            @RequestParam int quantity
    ) {
        stockService.decreaseStock(variantId, quantity);
        return ResponseEntity.ok(Map.of(
                "message", "Stock decreased successfully",
                "variantId", variantId,
                "quantity", quantity
        ));
    }

    /**
     * Increase stock (used for restocking or returns).
     * Example: POST /api/stock/{variantId}/increase?quantity=10
     */
    @PostMapping("/{variantId}/increase")
    public ResponseEntity<Map<String, Object>> increaseStock(
            @PathVariable UUID variantId,
            @RequestParam int quantity
    ) {
        stockService.increaseStock(variantId, quantity);
        return ResponseEntity.ok(Map.of(
                "message", "Stock increased successfully",
                "variantId", variantId,
                "quantity", quantity
        ));
    }
}

