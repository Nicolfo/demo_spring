package com.example.prova_spring_java.Products;

import org.springframework.stereotype.Service;

import java.util.List;


public interface ProductService {

    List<Product> getAllProducts();
    Long addProduct(ProductDTO productDTO);
    Product getProduct(Long key);
    public void removeProduct(Long key);
}
