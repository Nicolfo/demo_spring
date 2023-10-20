package com.example.prova_spring_java;

import com.example.prova_spring_java.Products.Product;
import com.example.prova_spring_java.Products.ProductDTO;
import com.example.prova_spring_java.Products.ProductRepository;
import com.example.prova_spring_java.Products.ProductService;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductControllerIntegrationTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductRepository productRepository; // Assume you have a ProductRepository
    @Autowired
    private ProductService productService;
    // You can still mock your service if needed

    @BeforeAll
    public void setup() {
        // Create a list of products for testing
        List<ProductDTO> products = new ArrayList<>();
        products.add(new ProductDTO("First Product", "Product 1",5.5));
        products.add(new ProductDTO("Second Product", "Product 2",2.2));

        productService.addProduct(products.get(0));
        productService.addProduct(products.get(1));
    }


    @Test
    public void testGetAllProducts() throws Exception {
        //Test API Call
        List<ProductDTO> products = new ArrayList<>();
        products.add(new ProductDTO("First Product", "Product 1",5.5));
        products.add(new ProductDTO("Second Product", "Product 2",2.2));
        //Test API Call
        mockMvc.perform(MockMvcRequestBuilders.get("/API/Products/GetAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(products.get(0).getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value(products.get(0).getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].price").value(products.get(0).getPrice()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(products.get(1).getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].description").value(products.get(1).getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].price").value(products.get(1).getPrice()));
    }


    @Test
    public void testAddProduct() throws Exception {
        ProductDTO product_test= new ProductDTO("First Product", "Product 1",5.5);

        //Generate a JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        String productJson = objectMapper.writeValueAsString(product_test);
        // Perform a PUT request to "/API/Products/AddProduct" and validate the response
        MvcResult result=mockMvc.perform(MockMvcRequestBuilders.put("/API/Products/AddProduct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson)) // JSON representation of the ProductDTO
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        //get response key and parse it as Long
        String key=result.getResponse().getContentAsString();
        Long keyL=Long.parseLong(key);

        //Check if the added Product match the input value
        Product added_product = productRepository.findById(keyL).orElse(null);

        Assert.notNull(added_product,"Product not added correctly (parsed as null)");
        Assert.isTrue(added_product.getName()!=product_test.getName(),"Invalid Name value");
        Assert.isTrue(added_product.getDescription()!=product_test.getDescription(),"Invalid Description value");
        Assert.isTrue(added_product.getPrice()!=product_test.getPrice(),"Invalid Price value");

        productRepository.deleteById(keyL);

    }
}

