package com.example.aa_project.Controller;

import com.example.aa_project.Datasource.GebruikerDAO;
import com.example.aa_project.Model.Gebruiker;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class GebruikerController {

    public GebruikerDAO gebruikerDAO;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public GebruikerController(GebruikerDAO gebruikerDAO, PasswordEncoder passwordEncoder) {

        this.gebruikerDAO = gebruikerDAO;
        this.passwordEncoder = passwordEncoder;

    }

    @GetMapping
    public Iterable<Gebruiker> getAllUsers() {

        return gebruikerDAO.findAll();

    }

    @GetMapping("/{id}")
    public ResponseEntity<Gebruiker> getUserById(@PathVariable int id){

        return gebruikerDAO.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Gebruiker> addUser(@Valid @RequestBody Gebruiker gebruiker){

        gebruiker.setPassword(
                passwordEncoder.encode(gebruiker.getPassword())
        );

        Gebruiker savedUser = gebruikerDAO.save(gebruiker);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedUser);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Gebruiker> deleteUser(@PathVariable int id) {

        if (gebruikerDAO.existsById(id)) {
            gebruikerDAO.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
