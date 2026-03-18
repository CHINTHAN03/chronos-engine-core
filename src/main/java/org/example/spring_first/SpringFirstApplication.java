package org.example.spring_first;

import org.example.spring_first.dto.RegisterRequest;
import org.example.spring_first.repository.AppUserRepository;
import org.example.spring_first.service.AuthenticationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.TimeZone;

@SpringBootApplication
public class SpringFirstApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
        SpringApplication.run(SpringFirstApplication.class, args);
    }


}