package com.mhyusuf.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleItemDto {

    private UUID id;

    private UUID variantId;

    private String variantName; // optional convenience field

    private Integer quantity;

    private BigDecimal subtotal;
}
