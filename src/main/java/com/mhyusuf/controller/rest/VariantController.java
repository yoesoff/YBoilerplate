package com.mhyusuf.controller.rest;

import com.mhyusuf.dto.VariantDto;
import com.mhyusuf.service.VariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST Controller for managing item variants.
 * Provides CRUD operations and integrates cleanly with exception handling.
 */
@RestController
@RequestMapping("/api/variants")
@RequiredArgsConstructor
public class VariantController {

    private final VariantService variantService;

    /**
     * Retrieve all variants.
     * Example: GET /api/variants
     */
    @GetMapping
    public ResponseEntity<List<VariantDto>> getAllVariants() {
        List<VariantDto> variants = variantService.getAllVariants();
        return ResponseEntity.ok(variants);
    }

    /**
     * Retrieve a single variant by its UUID.
     * Example: GET /api/variants/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<VariantDto> getVariantById(@PathVariable UUID id) {
        VariantDto variant = variantService.getVariantById(id);
        return ResponseEntity.ok(variant);
    }

    /**
     * Create a new variant for an existing item.
     * Example: POST /api/variants
     * Request Body:
     * {
     *   "itemId": "d9f6a8b3-93d4-4a7e-9b78-5a5dfd871a11",
     *   "variantName": "Large / Red",
     *   "color": "Red",
     *   "size": "L",
     *   "price": 299000,
     *   "stock": 10
     * }
     */
    @PostMapping
    public ResponseEntity<VariantDto> createVariant(@RequestBody VariantDto dto) {
        VariantDto created = variantService.createVariant(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Update an existing variant.
     * Example: PUT /api/variants/{id}
     * Request Body:
     * {
     *   "variantName": "Medium / Blue",
     *   "color": "Blue",
     *   "size": "M",
     *   "price": 279000,
     *   "stock": 5
     * }
     */
    @PutMapping("/{id}")
    public ResponseEntity<VariantDto> updateVariant(@PathVariable UUID id, @RequestBody VariantDto dto) {
        VariantDto updated = variantService.updateVariant(id, dto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Delete a variant by ID.
     * Example: DELETE /api/variants/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVariant(@PathVariable UUID id) {
        variantService.deleteVariant(id);
        return ResponseEntity.noContent().build();
    }
}

