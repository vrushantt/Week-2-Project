package com.vrushant.ratingsystemapplication.Repository;

import com.vrushant.ratingsystemapplication.Model.Rating;
import com.vrushant.ratingsystemapplication.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating,Long> , JpaSpecificationExecutor<Rating> {

    Optional<Rating> findByUser(User user);
    @Query("SELECT AVG(r.ambiance), AVG(r.food), AVG(r.service), AVG(r.cleanliness), AVG(r.drinks) FROM Rating r")
    List<Object[]> getAverageRatings();
    List<Rating> findAll();


    List<Rating> findByAmbianceAndFood(int ambiance, int food);
}
