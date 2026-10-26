package com.example.aa_project.Controller;

import com.example.aa_project.Datasource.ReviewDAO;
import com.example.aa_project.Model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
public class ReviewController {

    public ReviewDAO reviewDAO;

    @Autowired
    public ReviewController(ReviewDAO reviewDAO){

        this.reviewDAO = reviewDAO;

    }

    @GetMapping
    public Iterable<Review> getAllReviews() {

        return reviewDAO.findAll();

    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable int id){

        return reviewDAO.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Review> addReview(@RequestBody Review review){

        Review savedReview  = reviewDAO.save(review);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedReview);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Review> deleteReview(@PathVariable int id) {

        if (reviewDAO.existsById(id)) {
            reviewDAO.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
