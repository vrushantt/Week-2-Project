package com.vrushant.ratingsystemapplication.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class Rating {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private User user;

    private int ambiance;
    private int food;
    private int service;
    private int cleanliness;
    private int drinks;

    public Rating() {

    }
}
