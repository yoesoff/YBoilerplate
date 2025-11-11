package com.mhyusuf.service;

import com.mhyusuf.dto.ItemDto;
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

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;
    private final VariantRepository variantRepository;

    /**
     * Retrieve all items with their variants.
     */
    public List<ItemDto> getAllItems() {
        return itemRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Retrieve a single item by ID.
     */
    public ItemDto getItemById(UUID id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + id));
        return toDto(item);
    }

    /**
     * Create a new item.
     */
    public ItemDto createItem(ItemDto dto) {
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new BadRequestException("Item name must not be empty");
        }

        if (itemRepository.existsByName(dto.getName())) {
            throw new BadRequestException("Item with the same name already exists");
        }

        Item item = new Item(dto.getName(), dto.getDescription());
        item.setCreatedBy(dto.getCreatedBy());
        item.setUpdatedBy(dto.getUpdatedBy());
        Item saved = itemRepository.save(item);

        return toDto(saved);
    }

    /**
     * Update an existing item.
     */
    public ItemDto updateItem(UUID id, ItemDto dto) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + id));

        if (dto.getName() != null && !dto.getName().trim().isEmpty()) {
            // Cek jika nama baru sudah digunakan oleh item lain
            Optional<Item> existing = itemRepository.findByName(dto.getName());
            if (existing.isPresent() && !existing.get().getId().equals(id)) {
                throw new BadRequestException("Item name already used by another record");
            }
            item.setName(dto.getName());
        }

        item.setDescription(dto.getDescription());
        item.setUpdatedAt(new Date());
        item.setUpdatedBy(dto.getUpdatedBy());

        Item updated = itemRepository.save(item);
        return toDto(updated);
    }

    /**
     * Delete item by ID.
     */
    public void deleteItem(UUID id) {
        if (!itemRepository.existsById(id)) {
            throw new ResourceNotFoundException("Item not found with id: " + id);
        }
        itemRepository.deleteById(id);
    }

    /**
     * Helper method to convert Entity -> DTO.
     */
    private ItemDto toDto(Item item) {
        ItemDto dto = new ItemDto();
        dto.setId(item.getId());
        dto.setName(item.getName());
        dto.setDescription(item.getDescription());
        dto.setCreatedBy(item.getCreatedBy());
        dto.setUpdatedBy(item.getUpdatedBy());
        dto.setCreatedAt(item.getCreatedAt());
        dto.setUpdatedAt(item.getUpdatedAt());

        List<VariantDto> variantDtos = item.getVariants()
                .stream()
                .map(v -> new VariantDto(
                        v.getId(),
                        item.getId(),
                        v.getVariantName(),
                        v.getColor(),
                        v.getSize(),
                        v.getPrice(),
                        v.getStock(),
                        v.getCreatedBy(),
                        v.getUpdatedBy(),
                        v.getCreatedAt(),
                        v.getUpdatedAt()
                )).collect(Collectors.toList());

        dto.setVariants(variantDtos);
        return dto;
    }
}
