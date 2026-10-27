package com.example.aa_project.Datasource;

import com.example.aa_project.Model.Gebruiker;
import com.example.aa_project.Model.Reservatie;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReservatieDAO extends CrudRepository<Reservatie, Integer> {

    List<Reservatie> findByGebruikerAndStatus(
            Gebruiker gebruiker,
            boolean status
    );

}
