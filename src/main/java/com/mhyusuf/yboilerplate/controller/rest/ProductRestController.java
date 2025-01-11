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

    // DELETE endpoint to delete a product by id
    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable String id) {
        productList.removeIf(product -> product.getId().equals(id));
    }

    // PUT endpoint to update a product by id
    @PutMapping("/products/{id}")
    public Product updateProduct(@PathVariable String id, @RequestBody Product updatedProduct) {
        for (Product product : productList) {
            if (product.getId().equals(id)) {
                product.setName(updatedProduct.getName());
                return product;
            }
        }
        return null;  // or throw an exception if the product is not found
    }
}