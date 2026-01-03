package com.gymmanagement.reportanalytics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.gymmanagement.reportanalytics")
@EntityScan(basePackages = "com.gymmanagement.reportanalytics")
public class ReportAnalyticsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReportAnalyticsApplication.class, args);
    }
}

