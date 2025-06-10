package com.vrushant.ratingsystemapplication.Model;

import lombok.Data;

@Data

public class DTO {
    private Long id;
    private int ambiance, food, service, cleanliness, drinks;
    private String userEmail;
    // getters/setters
}
