package com.example.aa_project.Controller;

import com.example.aa_project.Datasource.GebruikerDAO;
import com.example.aa_project.Model.Gebruiker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class GebruikerController {

    public GebruikerDAO gebruikerDAO;

    @Autowired
    public GebruikerController(GebruikerDAO gebruikerDAO){

        this.gebruikerDAO = gebruikerDAO;

    }

    @GetMapping("/{id}")
    public ResponseEntity<Gebruiker> getUserById(@PathVariable int id){

        return gebruikerDAO.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Gebruiker> addUser(@RequestBody Gebruiker gebruiker){

        Gebruiker savedUser = gebruikerDAO.save(gebruiker);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedUser);

    }


}
