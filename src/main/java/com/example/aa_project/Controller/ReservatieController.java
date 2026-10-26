package com.example.aa_project.Controller;

import com.example.aa_project.Datasource.ReservatieDAO;
import com.example.aa_project.Model.Reservatie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservatie")
public class ReservatieController {

    public ReservatieDAO reservatieDAO;

    @Autowired
    public ReservatieController(ReservatieDAO reservatieDAO){
        this.reservatieDAO = reservatieDAO;
    }

    @GetMapping
    public Iterable<Reservatie> getAllReservations() {

        return reservatieDAO.findAll();

    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservatie> getReservationById(@PathVariable int id){

        return reservatieDAO.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Reservatie> addReservation(@RequestBody Reservatie reservatie){

        Reservatie savedReservation = reservatieDAO.save(reservatie);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedReservation);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Reservatie> deleteReservation(@PathVariable int id) {

        if (reservatieDAO.existsById(id)) {
            reservatieDAO.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
