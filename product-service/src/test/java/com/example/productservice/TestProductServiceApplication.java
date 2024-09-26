package com.example.productservice;

import ca.gbc.productservice.ProductServiceApplication;
import org.springframework.boot.SpringApplication;

public class TestProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(ProductServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
