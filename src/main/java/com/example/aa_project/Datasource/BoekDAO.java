package com.example.aa_project.Datasource;

import com.example.aa_project.Model.Boek;
import org.springframework.data.repository.CrudRepository;

public interface BoekDAO extends CrudRepository <Boek, String> {



}
