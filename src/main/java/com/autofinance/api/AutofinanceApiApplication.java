package com.autofinance.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AutofinanceApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutofinanceApiApplication.class, args);
    }

}
