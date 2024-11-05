package com.mhyusuf.yboilerplate.controller;

import com.mhyusuf.yboilerplate.entity.MongoProduct;
import com.mhyusuf.yboilerplate.service.MongoProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class MongoProductController {
    private final MongoProductService productService;

    @Autowired
    public MongoProductController(MongoProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<MongoProduct> getAllMongoProducts() {
        return productService.getAllMongoProducts();
    }

    @GetMapping("/{id}")
    public MongoProduct getMongoProductById(@PathVariable String id) {
        return productService.getMongoProductById(id);
    }

    @PostMapping
    public MongoProduct addMongoProduct(@RequestBody MongoProduct product) {
        return productService.addMongoProduct(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MongoProduct> updateProduct(@PathVariable String id, @RequestBody MongoProduct productDetails) {
        Optional<MongoProduct> updatedProduct = productService.updateProduct(id, productDetails);

        return updatedProduct
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public void deleteMongoProduct(@PathVariable String id) {
        productService.deleteMongoProduct(id);
    }
}
