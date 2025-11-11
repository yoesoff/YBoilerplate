package com.mhyusuf.dto;

import lombok.*;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {

    private UUID id;
    private String name;
    private String description;

    private List<VariantDto> variants = new ArrayList<>();

    private String createdBy;
    private String updatedBy;
    private Date createdAt;
    private Date updatedAt;
}
