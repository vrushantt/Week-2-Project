package com.vrushant.ratingsystemapplication.Model; // Assuming this is your package

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table; // Import the Table annotation
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "app_users") // Or "users", "tbl_user", etc. Choose a non-reserved name.
@Data
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    private String role; // "USER" or "ADMIN"

    public User() {
        // Default constructor is often needed by JPA
    }

    // Lombok's @AllArgsConstructor will create this, but if you remove Lombok or need specific logic:
    // public User(Long id, String name, String email, String password, String role) {
    //    this.id = id;
    //    this.name = name;
    //    this.email = email;
    //    this.password = password;
    //    this.role = role;
    // }
}