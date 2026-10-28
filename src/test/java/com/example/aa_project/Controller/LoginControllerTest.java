package com.example.aa_project.Controller;

import com.example.aa_project.Datasource.GebruikerDAO;
import com.example.aa_project.Model.Gebruiker;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoginControllerTest {

    @Mock
    private GebruikerDAO gebruikerDAO;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private Model model;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private LoginController loginController;

    private Gebruiker gebruiker;

    @BeforeEach
    void setUp() {
        gebruiker = new Gebruiker();
        gebruiker.setEmail("test@example.com");
        gebruiker.setPassword("wachtwoord123");
    }

    @Test
    void register() {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(gebruikerDAO.existsByEmail("test@example.com"))
                .thenReturn(false);
        when(passwordEncoder.encode("wachtwoord123"))
                .thenReturn("gehasht-wachtwoord");

        String resultaat = loginController.register(
                gebruiker,
                bindingResult,
                model,
                request,
                response
        );

        assertEquals("redirect:/", resultaat);

        assertEquals("gehasht-wachtwoord", gebruiker.getPassword());

        verify(gebruikerDAO).save(gebruiker);
        verify(passwordEncoder).encode("wachtwoord123");
    }

}
