package com.mhyusuf.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleDto {

    private UUID id;

    private String customerName;

    private BigDecimal totalAmount;

    private LocalDateTime createdAt;

    @Builder.Default
    private List<SaleItemDto> items = new ArrayList<>();
}
