package com.alura.forohub;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ForohubApplication {

    public static void main(String[] args) {
        SpringApplication.run(ForohubApplication.class, args);
    }

    @Bean
    public CommandLineRunner run() {
        return args -> {
            System.out.println("----------------------------------------------");
            System.out.println("HASH GENERADO PARA JWT_SECRET: " + new BCryptPasswordEncoder().encode("JWT_SECRET"));
            System.out.println("----------------------------------------------");
        };
}
}
