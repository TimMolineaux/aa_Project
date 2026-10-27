package com.example.aa_project.Datasource;

import com.example.aa_project.Model.Aankoop;
import com.example.aa_project.Model.Gebruiker;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AankoopDAO extends CrudRepository<Aankoop, Integer> {

    List<Aankoop> findByGebruiker(Gebruiker gebruiker);

}
