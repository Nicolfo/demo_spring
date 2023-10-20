package com.example.prova_spring_java.Products;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository=productRepository;
    }
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Long addProduct(ProductDTO productDTO) {
        Product toAdd = new Product(productDTO.getName(),productDTO.getDescription(),productDTO.getPrice());
        productRepository.save(toAdd);
        return toAdd.getId();
    }

    @Override
    public Product getProduct(Long key) {
       return productRepository.findById(key).get();   // throws exception if not found
    }

    @Override
    public void removeProduct(Long key) {
         productRepository.deleteById(key);
    }
}
