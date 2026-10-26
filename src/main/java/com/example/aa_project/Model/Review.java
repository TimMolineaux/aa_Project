package com.example.aa_project.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reviewId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private Gebruiker gebruiker;

    @ManyToOne
    @JoinColumn(name = "isbn", nullable = false)
    private Boek boek;

    @Size(min = 30, max = 500)
    @NotBlank
    private String description;

    @Max(value = 5)
    @Min(value = 1)
    @NotNull
    private int score;

    private LocalDate postDate;

    public Review(){

    }

    public Review(int reviewId, Gebruiker gebruiker, Boek boek, String description, int score, LocalDate postDate) {
        this.reviewId = reviewId;
        this.gebruiker = gebruiker;
        this.boek = boek;
        this.description = description;
        this.score = score;
        this.postDate = LocalDate.now();
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public Gebruiker getGebruiker() {
        return gebruiker;
    }

    public void setGebruiker(Gebruiker gebruiker) {
        this.gebruiker = gebruiker;
    }

    public Boek getBoek() {
        return boek;
    }

    public void setBoek(Boek boek) {
        this.boek = boek;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public LocalDate getPostDate() {
        return postDate;
    }

    public void setPostDate(LocalDate postDate) {
        this.postDate = postDate;
    }
}
