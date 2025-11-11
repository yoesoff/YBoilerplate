package com.mhyusuf.dto;

import lombok.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockDto {

    private UUID variantId;
    private Integer stock;
}
