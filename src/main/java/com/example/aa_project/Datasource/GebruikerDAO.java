package com.example.aa_project.Datasource;

import com.example.aa_project.Model.Gebruiker;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GebruikerDAO extends CrudRepository<Gebruiker, Integer> {

    Optional<Gebruiker> findByEmail(String email);

    boolean existsByEmail(String email);

}
