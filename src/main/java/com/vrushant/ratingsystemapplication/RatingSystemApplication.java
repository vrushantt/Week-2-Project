package com.vrushant.ratingsystemapplication;

import com.vrushant.ratingsystemapplication.Model.User;
import com.vrushant.ratingsystemapplication.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RatingSystemApplication {

    public static void main(String[] args) {

        SpringApplication.run(RatingSystemApplication.class, args);
    }

    @Bean
    CommandLineRunner createAdmin(UserRepository userRepo) {
        return args -> {
            if (userRepo.findByEmail("admin@email.com").isEmpty()) {
                User admin = new User();
                admin.setName("Admin");
                admin.setEmail("admin@email.com");
                admin.setPassword("admin123"); // No hashing for simplicity
                admin.setRole("ADMIN");
                userRepo.save(admin);
                System.out.println("Admin user created: admin@email.com / admin123");
            }
        };

    }
};

