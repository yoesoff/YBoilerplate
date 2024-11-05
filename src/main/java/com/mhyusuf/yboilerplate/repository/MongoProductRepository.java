package com.mhyusuf.yboilerplate.repository;

import com.mhyusuf.yboilerplate.entity.MongoProduct;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoProductRepository extends MongoRepository<MongoProduct, String> {
}