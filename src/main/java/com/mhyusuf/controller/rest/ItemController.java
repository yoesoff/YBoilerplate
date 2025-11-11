package com.mhyusuf.controller.rest;

import com.mhyusuf.dto.ItemDto;
import com.mhyusuf.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST Controller for managing shop items.
 * Provides CRUD endpoints and returns clean JSON responses.
 */
@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    /**
     * Get all items with their variants.
     * Example: GET /api/items
     */
    @GetMapping
    public ResponseEntity<List<ItemDto>> getAllItems() {
        List<ItemDto> items = itemService.getAllItems();
        return ResponseEntity.ok(items);
    }

    /**
     * Get an item by its UUID.
     * Example: GET /api/items/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ItemDto> getItemById(@PathVariable UUID id) {
        ItemDto item = itemService.getItemById(id);
        return ResponseEntity.ok(item);
    }

    /**
     * Create a new item.
     * Example: POST /api/items
     * Request Body:
     * {
     *   "name": "T-Shirt",
     *   "description": "Premium cotton white t-shirt"
     * }
     */
    @PostMapping
    public ResponseEntity<ItemDto> createItem(@RequestBody ItemDto dto) {
        ItemDto created = itemService.createItem(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Update existing item.
     * Example: PUT /api/items/{id}
     * Request Body:
     * {
     *   "name": "Updated Name",
     *   "description": "Updated description"
     * }
     */
    @PutMapping("/{id}")
    public ResponseEntity<ItemDto> updateItem(@PathVariable UUID id, @RequestBody ItemDto dto) {
        ItemDto updated = itemService.updateItem(id, dto);
        return ResponseEntity.ok(updated);
    }

    /**
     * Delete an item by ID.
     * Example: DELETE /api/items/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable UUID id) {
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}

