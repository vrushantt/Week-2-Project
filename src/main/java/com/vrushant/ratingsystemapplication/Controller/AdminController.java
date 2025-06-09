package com.vrushant.ratingsystemapplication.Controller;

import com.vrushant.ratingsystemapplication.Repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private RatingRepository ratingRepo;

    @GetMapping("/ratings")
    public ResponseEntity<?> filter(@RequestParam int ambiance, @RequestParam int food) {
        return ResponseEntity.ok(ratingRepo.findByAmbianceAndFood(ambiance, food));
    }

    @GetMapping("/report")
    public ResponseEntity<?> report(Authentication auth) {
        System.out.println("Logged in as: " + auth.getName());
        System.out.println("Authorities: " + auth.getAuthorities());
        Object[] avg = ratingRepo.getAverageRatings();
        double overall = (Arrays.stream(avg).mapToDouble(o -> (Double)o).sum()) / 5;
        return ResponseEntity.ok(Map.of(
                "ambiance", avg[0],
                "food", avg[1],
                "service", avg[2],
                "cleanliness", avg[3],
                "drinks", avg[4],
                "overallAverage", overall
        ));
    }
}