package com.cannabase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.cannabase")
public class CannabaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(CannabaseApplication.class, args);
    }
}