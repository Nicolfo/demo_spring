package com.example.prova_spring_java.Products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {           //automatically called by Spring
        this.productService = productService;
    }
    @GetMapping("/API/Products/GetAll")
    List<Product> getAllProducts(){
        return productService.getAllProducts();
    }
    @PutMapping("/API/Products/AddProduct")
    Long addProduct(@RequestBody ProductDTO productDTO){
        return productService.addProduct(productDTO);
    }
    @GetMapping("/API/Products/AddProduct/{key}")
    Product getProduct(@PathVariable Long key){
        return productService.getProduct(key);
    }

}
