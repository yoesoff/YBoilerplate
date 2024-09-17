package com.mhyusuf.yboilerplate.controller.rest;

import com.mhyusuf.yboilerplate.model.Product;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class ProductRestController {

    // List to store products
    private List<Product> productList = new ArrayList<>();

    public ProductRestController() {
        productList.add(new Product("1", "Honey"));
        productList.add(new Product("2", "Almond"));
    }

    // GET endpoint to retrieve all products
    @GetMapping("/products")
    public List<Product> getProducts() {
        return productList;
    }

    // POST endpoint to add a new product
    @PostMapping("/products")
    public Product addProduct(@RequestBody Product newProduct) {
        productList.add(newProduct);
        return newProduct;  // return the added product as a response
    }
}

