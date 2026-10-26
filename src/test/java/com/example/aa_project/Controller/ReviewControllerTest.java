package com.example.aa_project.Controller;

import com.example.aa_project.Datasource.ReviewDAO;
import com.example.aa_project.Model.Review;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewControllerTest {

    @Mock
    private ReviewDAO reviewDAO;

    @InjectMocks
    private ReviewController reviewController;

    @Test void getAllReviews(){

        Review review1 = new Review();
        Review review2 = new Review();

        when(reviewDAO.findAll()).thenReturn(List.of(review1, review2));

        Iterable<Review> reviews = reviewController.getAllReviews();

        assertNotNull(reviews);
        assertEquals(2, ((Collection<?>) reviews).size());

        verify(reviewDAO).findAll();

    }

    @Test
    void getReviewById(){

        Review review = new Review();

        when(reviewDAO.findById(1)).thenReturn(Optional.of(review));

        ResponseEntity<Review> response = reviewController.getReviewById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(review, response.getBody());

        verify(reviewDAO).findById(1);

    }

    @Test
    void addReview(){
        Review review = new Review();

        when(reviewDAO.save(review)).thenReturn(review);

        ResponseEntity<Review> response = reviewController.addReview(review);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(review, response.getBody());

        verify(reviewDAO).save(review);
    }

    @Test
    void deleteReview() {
        when(reviewDAO.existsById(1)).thenReturn(true);

        ResponseEntity<Review> response = reviewController.deleteReview(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(reviewDAO).deleteById(1);
    }

}
