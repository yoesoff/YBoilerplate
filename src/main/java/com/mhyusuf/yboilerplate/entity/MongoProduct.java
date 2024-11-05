package com.mhyusuf.yboilerplate.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.management.ConstructorParameters;

@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
@Document(collection = "products")
public class MongoProduct {
    @Id
    @NonNull private String id;
    @NonNull private String name;
    @NonNull private Double price;

}
