package com.vrushant.ratingsystemapplication.Spec;

import com.vrushant.ratingsystemapplication.Model.Rating;
import org.springframework.data.jpa.domain.Specification;

//// This class contains static methods to create specifications for filtering ratings based on various criteria.
public class RatingSpecifications {
    public static Specification<Rating> ambianceEq(Integer a) {
        return (root, query, cb) -> a == null ? null : cb.equal(root.get("ambiance"), a);
    }
    public static Specification<Rating> foodEq(Integer f) {
        return (root, query, cb) -> f == null ? null : cb.equal(root.get("food"), f);
    }
    public static Specification<Rating> serviceEq(Integer s) {
        return (root, query, cb) -> s == null ? null : cb.equal(root.get("service"), s);
    }
    public static Specification<Rating> cleanlinessEq(Integer c) {
        return (root, query, cb) -> c == null ? null : cb.equal(root.get("cleanliness"), c);
    }
    public static Specification<Rating> drinksEq(Integer d) {
        return (root, query, cb) -> d == null ? null : cb.equal(root.get("drinks"), d);
    }
}
