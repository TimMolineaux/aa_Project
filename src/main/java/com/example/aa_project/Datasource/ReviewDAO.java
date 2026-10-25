package com.example.aa_project.Datasource;

import com.example.aa_project.Model.Review;
import org.springframework.data.repository.CrudRepository;

public interface ReviewDAO extends CrudRepository<Review, Integer> {
}
