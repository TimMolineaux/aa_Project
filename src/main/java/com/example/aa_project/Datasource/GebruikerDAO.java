package com.example.aa_project.Datasource;

import com.example.aa_project.Model.Gebruiker;
import org.springframework.data.repository.CrudRepository;

public interface GebruikerDAO extends CrudRepository<Gebruiker, Integer> {
}
