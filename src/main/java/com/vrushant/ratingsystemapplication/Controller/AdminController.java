package com.vrushant.ratingsystemapplication.Controller;

import com.vrushant.ratingsystemapplication.Model.RatingRequest;
import com.vrushant.ratingsystemapplication.Repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private RatingRepository ratingRepo;

    @GetMapping("/ratings")
    public ResponseEntity<?> filter(@RequestParam int ambiance, @RequestParam int food,Authentication auth) {
        if (auth != null) {
            System.out.println("Logged in as: " + auth.getName());
            System.out.println("Authorities: " + auth.getAuthorities());
        } else {
            System.out.println("Executing /admin/ratings as an anonymous user (Authentication object is null).");
        }
        return ResponseEntity.ok(ratingRepo.findByAmbianceAndFood(ambiance, food));
    }

    @GetMapping("/report")
    public ResponseEntity<?> report(Authentication auth) {
        if (auth != null) {
            System.out.println("Logged in as: " + auth.getName());
            System.out.println("Authorities: " + auth.getAuthorities());
        } else {
            System.out.println("Executing /admin/report as an anonymous user (Authentication object is null).");
        }

        Object[] avg = ratingRepo.getAverageRatings();
        // Safely convert each element to double, handling nulls and BigDecimal
        double[] avgs = new double[5];
        for (int i = 0; i < avg.length; i++) {
            Object o = avg[i];
            if (o == null) {
                avgs[i] = 0.0;
            } else if (o instanceof Number) {
                avgs[i] = ((Number) o).doubleValue();
            } else {
                avgs[i] = 0.0; // fallback - you might want to log or throw instead
            }
        }
        double overall = Arrays.stream(avgs).sum() / avgs.length;

        return ResponseEntity.ok(Map.of(
                "ambiance", avgs[0],
                "food", avgs[1],
                "service", avgs[2],
                "cleanliness", avgs[3],
                "drinks", avgs[4],
                "overallAverage", overall
        ));
    }
}