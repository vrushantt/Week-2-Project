package com.vrushant.ratingsystemapplication.Controller;

import com.vrushant.ratingsystemapplication.Model.Rating;
import com.vrushant.ratingsystemapplication.Model.RatingRequest;
import com.vrushant.ratingsystemapplication.Model.User;
import com.vrushant.ratingsystemapplication.Repository.RatingRepository;
import com.vrushant.ratingsystemapplication.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private RatingRepository ratingRepo;

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/rate")
    public ResponseEntity<?> rate(@RequestBody RatingRequest req,
                                  @AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.status(401).body("User not authenticated");
        }

        String email = user.getEmail();

        // Find the user by email from the database
        user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if this user has already rated
        if (ratingRepo.findByUser(user).isPresent()) {
            return ResponseEntity.badRequest().body("Already rated");
        }

        // Create and save the rating
        Rating rating = new Rating();
        rating.setUser(user);
        rating.setAmbiance(req.getAmbiance());
        rating.setFood(req.getFood());
        rating.setService(req.getService());
        rating.setCleanliness(req.getCleanliness());
        rating.setDrinks(req.getDrinks());

        ratingRepo.save(rating);

        return ResponseEntity.ok("Rating submitted");
    }
}
