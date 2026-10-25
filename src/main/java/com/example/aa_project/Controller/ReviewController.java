package com.example.aa_project.Controller;

import com.example.aa_project.Datasource.ReviewDAO;
import com.example.aa_project.Model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
