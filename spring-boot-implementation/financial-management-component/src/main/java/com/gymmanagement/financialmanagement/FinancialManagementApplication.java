package com.gymmanagement.financialmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.gymmanagement.financialmanagement")
@EntityScan(basePackages = "com.gymmanagement.base.entity")
public class FinancialManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinancialManagementApplication.class, args);
    }
}

