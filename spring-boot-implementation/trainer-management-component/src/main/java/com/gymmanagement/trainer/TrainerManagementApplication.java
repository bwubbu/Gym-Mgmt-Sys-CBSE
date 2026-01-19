package com.gymmanagement.trainer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.gymmanagement.trainer")
@EntityScan(basePackages = "com.gymmanagement.trainer")
public class TrainerManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrainerManagementApplication.class, args);
    }
}