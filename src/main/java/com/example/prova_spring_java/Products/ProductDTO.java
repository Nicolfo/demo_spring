package com.example.prova_spring_java.Products;

public class ProductDTO {
    public ProductDTO(String description, String name, Double price) {
        this.description = description;
        this.name = name;
        this.price = price;
    }

    private String description;
    private String name;
    private Double price;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}

