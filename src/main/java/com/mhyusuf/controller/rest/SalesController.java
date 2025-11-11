package com.mhyusuf.controller.rest;

import com.mhyusuf.dto.SaleDto;
import com.mhyusuf.service.SalesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * REST Controller for managing sales transactions.
 * Provides endpoints for creation, retrieval, and filtered querying of sales.
 */
@Slf4j
@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
public class SalesController {

    private final SalesService salesService;

    /**
     * Create a new sale transaction.
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createSale(@RequestBody SaleDto request) {
        log.info("Received sale creation request for customer: {}", request.getCustomerName());

        SaleDto createdSale = salesService.createSale(request);

        log.info("Sale created successfully with ID: {}", createdSale.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "message", "Sale created successfully",
                "sale", createdSale
        ));
    }

    /**
     * Retrieve all sales.
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllSales() {
        log.debug("Fetching all sales...");
        List<SaleDto> sales = salesService.getAllSales();
        return ResponseEntity.ok(Map.of(
                "count", sales.size(),
                "sales", sales
        ));
    }

    /**
     * Retrieve sale by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getSaleById(@PathVariable UUID id) {
        log.debug("Fetching sale details for ID: {}", id);
        SaleDto sale = salesService.getSaleById(id);
        return ResponseEntity.ok(Map.of(
                "message", "Sale retrieved successfully",
                "sale", sale
        ));
    }

    /**
     * Filter sales based on optional query parameters:
     * - startDate (ISO date-time)
     * - endDate (ISO date-time)
     * - customerName (partial match)
     * - minTotal (BigDecimal)
     * - maxTotal (BigDecimal)
     *
     * Example:
     * GET /api/sales/filter?startDate=2025-11-01T00:00:00&endDate=2025-11-11T23:59:59&minTotal=10000
     */
    @GetMapping("/filter")
    public ResponseEntity<Map<String, Object>> filterSales(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,

            @RequestParam(required = false) String customerName,

            @RequestParam(required = false) BigDecimal minTotal,

            @RequestParam(required = false) BigDecimal maxTotal
    ) {
        log.info("Filtering sales with params - startDate: {}, endDate: {}, customerName: {}, minTotal: {}, maxTotal: {}",
                startDate, endDate, customerName, minTotal, maxTotal);

        List<SaleDto> sales = salesService.filterSales(startDate, endDate, customerName, minTotal, maxTotal);

        log.info("Found {} matching sales", sales.size());
        return ResponseEntity.ok(Map.of(
                "filters", Map.of(
                        "startDate", startDate,
                        "endDate", endDate,
                        "customerName", customerName,
                        "minTotal", minTotal,
                        "maxTotal", maxTotal
                ),
                "count", sales.size(),
                "sales", sales
        ));
    }
}
