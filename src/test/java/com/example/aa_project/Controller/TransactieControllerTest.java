package com.example.aa_project.Controller;

import com.example.aa_project.Datasource.AankoopDAO;
import com.example.aa_project.Datasource.BoekDAO;
import com.example.aa_project.Datasource.GebruikerDAO;
import com.example.aa_project.Datasource.ReservatieDAO;
import com.example.aa_project.Model.Aankoop;
import com.example.aa_project.Model.Boek;
import com.example.aa_project.Model.Gebruiker;
import com.example.aa_project.Model.Reservatie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactieControllerTest {

    @Mock
    private BoekDAO boekDAO;

    @Mock
    private GebruikerDAO gebruikerDAO;

    @Mock
    private ReservatieDAO reservatieDAO;

    @Mock
    private AankoopDAO aankoopDAO;

    @InjectMocks
    private TransactieController transactieController;

    private Boek boek;
    private Gebruiker gebruiker;
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        boek = new Boek();
        boek.setIsbn("1234567890");
        boek.setVoorraad(10);

        gebruiker = new Gebruiker();
        gebruiker.setEmail("test@example.com");

        authentication = new UsernamePasswordAuthenticationToken(
                "test@example.com",
                "password",
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

    @Test
    void reserveBook() {
        when(boekDAO.findById("1234567890"))
                .thenReturn(Optional.of(boek));

        when(gebruikerDAO.findByEmail("test@example.com"))
                .thenReturn(Optional.of(gebruiker));

        RedirectAttributesModelMap attributes =
                new RedirectAttributesModelMap();

        String resultaat = transactieController.reserveBook(
                "1234567890", 2, authentication, attributes
        );

        assertEquals("redirect:/boeken/1234567890", resultaat);
        assertEquals(8, boek.getVoorraad());

        verify(boekDAO).save(boek);
        verify(reservatieDAO).save(any(Reservatie.class));
        verify(aankoopDAO).save(any(Aankoop.class));

        ArgumentCaptor<Reservatie> reservatieCaptor =
                ArgumentCaptor.forClass(Reservatie.class);

        verify(reservatieDAO).save(reservatieCaptor.capture());

        Reservatie reservatie = reservatieCaptor.getValue();

        assertEquals(2, reservatie.getAmount());
        assertEquals(boek, reservatie.getBoek());
        assertEquals(gebruiker, reservatie.getGebruiker());
        assertFalse(reservatie.isStatus());

        ArgumentCaptor<Aankoop> aankoopCaptor =
                ArgumentCaptor.forClass(Aankoop.class);

        verify(aankoopDAO).save(aankoopCaptor.capture());

        Aankoop aankoop = aankoopCaptor.getValue();

        assertEquals(2, aankoop.getAmount());
        assertEquals(boek, aankoop.getBoek());
        assertEquals(gebruiker, aankoop.getGebruiker());

        assertNotNull(attributes.getFlashAttributes()
                .get("bevestiging"));
    }

    @Test
    void refuseInsufficientStock() {
        when(boekDAO.findById("1234567890"))
                .thenReturn(Optional.of(boek));

        RedirectAttributesModelMap attributes =
                new RedirectAttributesModelMap();

        String resultaat = transactieController.reserveBook(
                "1234567890", 11, authentication, attributes
        );

        assertEquals("redirect:/boeken/1234567890", resultaat);
        assertEquals(10, boek.getVoorraad());

        assertNotNull(attributes.getFlashAttributes()
                .get("foutmelding"));

        verify(boekDAO, never()).save(any(Boek.class));
        verify(reservatieDAO, never()).save(any(Reservatie.class));
        verify(aankoopDAO, never()).save(any(Aankoop.class));
    }

    @Test
    void refuseAmmountLessThan0() {
        when(boekDAO.findById("1234567890"))
                .thenReturn(Optional.of(boek));

        RedirectAttributesModelMap attributes =
                new RedirectAttributesModelMap();

        String resultaat = transactieController.reserveBook(
                "1234567890", 0, authentication, attributes
        );

        assertEquals("redirect:/boeken/1234567890", resultaat);
        assertEquals(10, boek.getVoorraad());

        assertNotNull(attributes.getFlashAttributes()
                .get("foutmelding"));

        verify(boekDAO, never()).save(any(Boek.class));
        verify(reservatieDAO, never()).save(any(Reservatie.class));
        verify(aankoopDAO, never()).save(any(Aankoop.class));
    }

}
