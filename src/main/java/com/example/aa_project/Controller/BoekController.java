package com.example.aa_project.Controller;

import com.example.aa_project.Datasource.BoekDAO;
import com.example.aa_project.Model.Boek;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/boek")
public class BoekController {

    private BoekDAO boekDAO;

    @Autowired
    public BoekController(BoekDAO boekDAO){

        this.boekDAO = boekDAO;

    }

    @GetMapping
    public Iterable<Boek> getAllBooks() {
        return boekDAO.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Boek> getBookById(@PathVariable String id){

        if (boekDAO.existsById(id)) {
            Boek boek = boekDAO.findById(id).get();
            ResponseEntity<Boek> response = new ResponseEntity<>(boek, HttpStatus.OK);
            return response;
        }else {
            ResponseEntity<Boek> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return response;
        }

    }

    @PostMapping("/add")
    public ResponseEntity<Boek> addBook(@RequestBody Boek boek) {
        Boek savedBoek = boekDAO.save(boek);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedBoek);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Boek> deleteBook(@PathVariable String id){

        if (boekDAO.existsById(id)) {
            boekDAO.deleteById(id);
            return ResponseEntity.noContent().build();
        } else
        {
            return ResponseEntity.notFound().build();
        }

    }


}
