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

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GebruikerControllerTest {

    @Mock
    private GebruikerDAO gebruikerDAO;

    @InjectMocks
    private GebruikerController gebruikerController;

    @Test
    void getAllUsers(){

        Gebruiker gebruiker1 = new Gebruiker();
        Gebruiker gebruiker2 = new Gebruiker();

        when(gebruikerDAO.findAll()).thenReturn(List.of(gebruiker1, gebruiker2));

        Iterable<Gebruiker> gebruikers = gebruikerController.getAllUsers();

        assertNotNull(gebruikers);
        assertEquals(2, ((Collection<?>) gebruikers).size());

        verify(gebruikerDAO).findAll();

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
    void deleteUser() {
        when(gebruikerDAO.existsById(1)).thenReturn(true);

        ResponseEntity<Gebruiker> response = gebruikerController.deleteUser(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(gebruikerDAO).deleteById(1);
    }

}
