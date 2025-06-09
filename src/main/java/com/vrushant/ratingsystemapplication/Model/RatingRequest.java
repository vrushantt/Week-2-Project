package com.vrushant.ratingsystemapplication.Model;

import lombok.Data;

@Data
public class RatingRequest {
    public int ambiance;
    public int food;
    public int service;
    public int cleanliness;
    public int drinks;
}

