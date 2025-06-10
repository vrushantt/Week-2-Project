package com.vrushant.ratingsystemapplication.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;


@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })

@Entity
@Data
@AllArgsConstructor
public class Rating {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")  // foreign key column
    private User user;

    private int ambiance;
    private int food;
    private int service;
    private int cleanliness;
    private int drinks;

    public Rating() {

    }
}
