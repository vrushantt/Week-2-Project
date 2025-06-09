package com.vrushant.ratingsystemapplication.Repository;

import com.vrushant.ratingsystemapplication.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Method to find a user by their email address
    Optional<User> findByEmail(String email);
}
