package com.mhyusuf.yboilerplate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller public class ProductViewController {
    @GetMapping("/view-products")
    public String viewProducts() {
        return "products/view-products";
    }
    @GetMapping("/add-products")
    public String addProducts() {
        return "products/add-products";
    }
}
