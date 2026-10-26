package com.example.aa_project.Controller;

import com.example.aa_project.Datasource.AankoopDAO;
import com.example.aa_project.Model.Aankoop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aankoop")
public class AankoopController {

    public AankoopDAO aankoopDAO;

    @Autowired
    public AankoopController(AankoopDAO aankoopDAO){

        this.aankoopDAO = aankoopDAO;

    }

    @GetMapping
    public Iterable<Aankoop> getAllPurchases() {

        return aankoopDAO.findAll();

    }

    @GetMapping("/{id}")
    public ResponseEntity<Aankoop> getPurchasesById(@PathVariable int id){

        return aankoopDAO.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Aankoop> addPurchase(@RequestBody Aankoop aankoop){

        Aankoop savedPurchase = aankoopDAO.save(aankoop);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedPurchase);

    }

}
