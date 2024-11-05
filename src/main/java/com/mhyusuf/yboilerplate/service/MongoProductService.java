package com.mhyusuf.yboilerplate.service;

import com.mhyusuf.yboilerplate.entity.MongoProduct;
import com.mhyusuf.yboilerplate.repository.MongoProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MongoProductService {
    private final MongoProductRepository productRepository;

    @Autowired
    public MongoProductService(MongoProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<MongoProduct> getAllMongoProducts() {
        return productRepository.findAll();
    }

    public MongoProduct getMongoProductById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    public MongoProduct addMongoProduct(MongoProduct product) {
        return productRepository.save(product);
    }

    // Metode untuk update product berdasarkan ID
    public Optional<MongoProduct> updateProduct(String id, MongoProduct productDetails) {
        Optional<MongoProduct> productData = productRepository.findById(id);

        if (productData.isPresent()) {
            MongoProduct existingProduct = productData.get();
            existingProduct.setName(productDetails.getName());
            existingProduct.setPrice(productDetails.getPrice());
            MongoProduct updatedProduct = productRepository.save(existingProduct);
            return Optional.of(updatedProduct);
        } else {
            return Optional.empty();
        }
    }

    public void deleteMongoProduct(String id) {
        productRepository.deleteById(id);
    }
}
