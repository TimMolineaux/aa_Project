package com.example.aa_project.Datasource;

import com.example.aa_project.Model.Reservatie;
import org.springframework.data.repository.CrudRepository;

public interface ReservatieDAO extends CrudRepository<Reservatie, Integer> {
}
