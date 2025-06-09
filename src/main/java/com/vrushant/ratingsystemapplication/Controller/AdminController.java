package com.vrushant.ratingsystemapplication.Controller;

import com.vrushant.ratingsystemapplication.Model.Rating;
import com.vrushant.ratingsystemapplication.Model.RatingRequest;
import com.vrushant.ratingsystemapplication.Repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
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

    @GetMapping("/ratings/all")
    public ResponseEntity<List<Rating>> getAllRatings(Authentication auth) {
        if (auth != null) {
            System.out.println("Logged in as: " + auth.getName());
        }
        List<Rating> ratings = ratingRepo.findAll();
        return ResponseEntity.ok(ratings);
    }

    @GetMapping("/report")
    public ResponseEntity<?> report(Authentication auth) {
        if (auth != null) {
            System.out.println("Logged in as: " + auth.getName());
            System.out.println("Authorities: " + auth.getAuthorities());
        } else {
            System.out.println("Executing /admin/report as an anonymous user (Authentication object is null).");
        }

        List<Object[]> avgList = ratingRepo.getAverageRatings();
        Object[] avg = avgList.get(0); // only one row from AVG()

        double[] avgs = new double[5];
        for (int i = 0; i < avg.length; i++) {
            Object o = avg[i];
            if (o == null) {
                avgs[i] = 0.0;
            } else if (o instanceof Number) {
                avgs[i] = ((Number) o).doubleValue();
            } else {
                avgs[i] = 0.0;
            }
        }

        double overall = Arrays.stream(avgs).sum() / avgs.length;
        System.out.println("Raw avg result: " + Arrays.deepToString(avgList.toArray()));

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