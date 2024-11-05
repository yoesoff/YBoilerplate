package com.mhyusuf.yboilerplate;

import com.mhyusuf.yboilerplate.entity.MongoProduct;
import com.mhyusuf.yboilerplate.service.MongoProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MongoProductServiceTests {

    @Autowired
    private MongoProductService productService;

    @Test
    public void testAddProduct() {
        MongoProduct product = new MongoProduct("1", "Test Product", 10.0);
        MongoProduct savedProduct = productService.addMongoProduct(product);
        assertThat(savedProduct.getName()).isEqualTo("Test Product");
    }

    @Test
    public void testGetAllProducts() {
        List<MongoProduct> products = productService.getAllMongoProducts();
        assertThat(products).isNotNull();
    }
}
