package com.vrushant.ratingsystemapplication.Controller;

import com.vrushant.ratingsystemapplication.Model.DTO;
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
    public ResponseEntity<List<DTO>> filter(
            @RequestParam(required = false) Integer ambiance,
            @RequestParam(required = false) Integer food,
            Authentication auth) {

        System.out.println("FILTER HIT — auth=" + auth + ", params → ambiance: " + ambiance + ", food: " + food);

        List<DTO> dtos = ratingRepo.findByAmbianceAndFood(
                        ambiance != null ? ambiance : 0,
                        food != null ? food : 0
                ).stream()
                .map(r -> {
                    DTO d = new DTO();
                    d.setId(r.getId());
                    d.setUserEmail(r.getUser().getEmail());
                    d.setAmbiance(r.getAmbiance());
                    d.setFood(r.getFood());
                    d.setService(r.getService());
                    d.setCleanliness(r.getCleanliness());
                    d.setDrinks(r.getDrinks());
                    return d;
                }).toList();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/ratings/all")
    public ResponseEntity<List<DTO>> getAllRatings(Authentication auth) {
        System.out.println("ALL HIT — auth=" + auth);
        List<DTO> dtos = ratingRepo.findAll().stream()
                .map(r -> {
                    DTO d = new DTO();
                    d.setId(r.getId());
                    d.setUserEmail(r.getUser().getEmail());
                    d.setAmbiance(r.getAmbiance());
                    d.setFood(r.getFood());
                    d.setService(r.getService());
                    d.setCleanliness(r.getCleanliness());
                    d.setDrinks(r.getDrinks());
                    return d;
                }).toList();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/report")
    public ResponseEntity<?> report(Authentication auth) {
        System.out.println("REPORT HIT — auth=" + auth);

        Object[] avg = ratingRepo.getAverageRatings().get(0); // safe as DB has records
        double[] avgs = Arrays.stream(avg)
                .mapToDouble(o -> (o instanceof Number ? ((Number) o).doubleValue() : 0.0))
                .toArray();
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
