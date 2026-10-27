package com.example.aa_project.Datasource;

import com.example.aa_project.Model.Boek;
import com.example.aa_project.Model.Gebruiker;
import com.example.aa_project.Model.Review;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewDAO extends CrudRepository<Review, Integer> {

    Optional<Review> findByGebruikerAndBoek(Gebruiker gebruiker, Boek boek);

    List<Review> findByBoek(Boek boek);

}
