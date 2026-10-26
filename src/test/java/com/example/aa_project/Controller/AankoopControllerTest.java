package com.example.aa_project.Controller;

import com.example.aa_project.Datasource.AankoopDAO;
import com.example.aa_project.Model.Aankoop;
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
public class AankoopControllerTest {

    @Mock
    private AankoopDAO aankoopDAO;

    @InjectMocks
    private  AankoopController aankoopController;

    @Test
    void getAllPurchases(){

        Aankoop aankoop1 = new Aankoop();
        Aankoop aankoop2 = new Aankoop();

        when(aankoopDAO.findAll()).thenReturn(List.of(aankoop1, aankoop2));

        Iterable<Aankoop> aankopen = aankoopController.getAllPurchases();

        assertNotNull(aankopen);
        assertEquals(2, ((Collection<?>) aankopen).size());

        verify(aankoopDAO).findAll();
    }

    @Test
    void getPurchasesById(){

        Aankoop aankoop = new Aankoop();

        when(aankoopDAO.findById(1)).thenReturn(Optional.of(aankoop));

        ResponseEntity<Aankoop> response = aankoopController.getPurchasesById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(aankoop, response.getBody());

        verify(aankoopDAO).findById(1);

    }

    @Test
    void addPurchase(){

        Aankoop aankoop = new Aankoop();

        when(aankoopDAO.save(aankoop)).thenReturn(aankoop);

        ResponseEntity<Aankoop> response = aankoopController.addPurchase(aankoop);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(aankoop, response.getBody());

        verify(aankoopDAO).save(aankoop);
    }

}
