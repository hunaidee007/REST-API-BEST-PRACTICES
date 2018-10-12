package com.blogspot.sgdev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main Application Spring Boot Starter
 * @author s981549
 *
 */
@SpringBootApplication()
@ComponentScan("com.blogspot")
@EnableAutoConfiguration()
@EntityScan
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
