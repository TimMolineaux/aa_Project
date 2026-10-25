package com.example.aa_project.Controller;

import com.example.aa_project.Datasource.BoekDAO;
import com.example.aa_project.Model.Boek;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BoekControllerTest {

    @Mock
    private BoekDAO boekDAO;

    @InjectMocks
    private BoekController boekController;

    @Test
    void addBook(){
        Boek boek = new Boek();

        when(boekDAO.save(boek)).thenReturn(boek);

        ResponseEntity<Boek> response = boekController.addBook(boek);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(boek, response.getBody());

        verify(boekDAO).save(boek);
    }

    @Test
    void getAllBooks() {
        Boek boek1 = new Boek();
        Boek boek2 = new Boek();

        when(boekDAO.findAll()).thenReturn(List.of(boek1, boek2));

        Iterable<Boek> boeken = boekController.getAllBooks();

        assertNotNull(boeken);
        assertEquals(2, ((Collection<?>) boeken).size());

        verify(boekDAO).findAll();
    }

    @Test
    void getBookById(){

        Boek boek = new Boek();

        when(boekDAO.existsById("1")).thenReturn(true);
        when(boekDAO.findById("1")).thenReturn(Optional.of(boek));

        ResponseEntity<Boek> response = boekController.getBookById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(boek, response.getBody());

        verify(boekDAO).findById("1");

    }

    @Test
    void deleteBook() {
        when(boekDAO.existsById("1")).thenReturn(true);

        ResponseEntity<Boek> response = boekController.deleteBook("1");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(boekDAO).deleteById("1");
    }

}