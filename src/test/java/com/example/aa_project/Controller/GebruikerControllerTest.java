package com.example.aa_project.Controller;

import com.example.aa_project.Datasource.GebruikerDAO;
import com.example.aa_project.Model.Gebruiker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GebruikerControllerTest {

    @Mock
    private GebruikerDAO gebruikerDAO;

    @InjectMocks
    private GebruikerController gebruikerController;

    @Test
    void addUser(){
        Gebruiker gebruiker = new Gebruiker();

        when(gebruikerDAO.save(gebruiker)).thenReturn(gebruiker);

        ResponseEntity<Gebruiker> response = gebruikerController.addUser(gebruiker);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(gebruiker, response.getBody());

        verify(gebruikerDAO).save(gebruiker);
    }

    @Test
    void getUSerById(){

        Gebruiker gebruiker = new Gebruiker();

        when(gebruikerDAO.findById(1)).thenReturn(Optional.of(gebruiker));

        ResponseEntity<Gebruiker> response = gebruikerController.getUserById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(gebruiker, response.getBody());

        verify(gebruikerDAO).findById(1);

    }

}
