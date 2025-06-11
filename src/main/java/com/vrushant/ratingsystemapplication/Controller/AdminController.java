package com.vrushant.ratingsystemapplication.Controller;

import com.vrushant.ratingsystemapplication.Model.DTO;
import com.vrushant.ratingsystemapplication.Model.Rating;
import com.vrushant.ratingsystemapplication.Model.RatingRequest;
import com.vrushant.ratingsystemapplication.Repository.RatingRepository;
import com.vrushant.ratingsystemapplication.Spec.RatingSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
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


    @GetMapping("/ratings/filter")
    public ResponseEntity<List<DTO>> filterRatings(
            @RequestParam(required = false) Integer ambiance,
            @RequestParam(required = false) Integer food,
            @RequestParam(required = false) Integer service,
            @RequestParam(required = false) Integer cleanliness,
            @RequestParam(required = false) Integer drinks,
            Authentication auth) {

        System.out.println("FILTER HIT — auth=" + auth +
                ", params → ambiance=" + ambiance +
                ", food=" + food +
                ", service=" + service +
                ", cleanliness=" + cleanliness +
                ", drinks=" + drinks);

        // Create a specification based on the provided parameters
        Specification<Rating> spec = Specification
                .where(RatingSpecifications.ambianceEq(ambiance))
                .and(RatingSpecifications.foodEq(food))
                .and(RatingSpecifications.serviceEq(service))
                .and(RatingSpecifications.cleanlinessEq(cleanliness))
                .and(RatingSpecifications.drinksEq(drinks));

        List<DTO> dtos = ratingRepo.findAll(spec).stream()
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
                })
                .toList();

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
