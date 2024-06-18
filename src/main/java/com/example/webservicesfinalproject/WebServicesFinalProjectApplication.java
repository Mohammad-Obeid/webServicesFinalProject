package com.example.webservicesfinalproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.SecureRandom;
import java.util.Base64;

@SpringBootApplication
public class WebServicesFinalProjectApplication {


    public static void main(String[] args) {
//        byte[] key = new byte[32];
//        SecureRandom secureRandom = new SecureRandom();
//        secureRandom.nextBytes(key);
//        String secretKey = Base64.getEncoder().encodeToString(key);
//        System.out.println("Generated Secret Key: " + secretKey);

        SpringApplication.run(WebServicesFinalProjectApplication.class, args);

    }

}
