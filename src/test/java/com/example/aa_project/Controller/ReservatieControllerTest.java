package com.example.aa_project.Controller;

import com.example.aa_project.Datasource.ReservatieDAO;
import com.example.aa_project.Model.Reservatie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReservatieControllerTest {

    @Mock
    private ReservatieDAO reservatieDAO;

    @InjectMocks
    private ReservatieController reservatieController;

    @Test
    void getAllReservations(){

        Reservatie reservatie1 = new Reservatie();
        Reservatie reservatie2 = new Reservatie();

        when(reservatieDAO.findAll()).thenReturn(List.of(reservatie1, reservatie2));

        Iterable<Reservatie> reservaties = reservatieController.getAllReservations();

        assertNotNull(reservaties);
        assertEquals(2, ((Collection<?>) reservaties).size());

        verify(reservatieDAO).findAll();
    }

    @Test
    void getReservationById(){

        Reservatie reservatie = new Reservatie();

        when(reservatieDAO.findById(1)).thenReturn(Optional.of(reservatie));

        ResponseEntity<Reservatie> response = reservatieController.getReservationById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reservatie, response.getBody());

        verify(reservatieDAO).findById(1);

    }

    @Test
    void addReservation(){

        Reservatie reservatie = new Reservatie();

        when(reservatieDAO.save(reservatie)).thenReturn(reservatie);

        ResponseEntity<Reservatie> response = reservatieController.addReservation(reservatie);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(reservatie, response.getBody());

        verify(reservatieDAO).save(reservatie);
    }

    @Test
    void deleteReservation() {
        when(reservatieDAO.existsById(1)).thenReturn(true);

        ResponseEntity<Reservatie> response = reservatieController.deleteReservation(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(reservatieDAO).deleteById(1);
    }

}
