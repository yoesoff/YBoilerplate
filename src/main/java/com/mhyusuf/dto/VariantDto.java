package com.mhyusuf.dto;

import lombok.*;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VariantDto {

    private UUID id;
    private UUID itemId;
    private String variantName;
    private String color;
    private String size;
    private Double price;
    private Integer stock;

    private String createdBy;
    private String updatedBy;
    private Date createdAt;
    private Date updatedAt;
}
