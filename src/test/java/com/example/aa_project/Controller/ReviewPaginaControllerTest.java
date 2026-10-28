package com.example.aa_project.Controller;

import com.example.aa_project.Datasource.AankoopDAO;
import com.example.aa_project.Datasource.BoekDAO;
import com.example.aa_project.Datasource.GebruikerDAO;
import com.example.aa_project.Datasource.ReviewDAO;
import com.example.aa_project.Model.Boek;
import com.example.aa_project.Model.Gebruiker;
import com.example.aa_project.Model.Review;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewPaginaControllerTest {

    @Mock
    private BoekDAO boekDAO;

    @Mock
    private GebruikerDAO gebruikerDAO;

    @Mock
    private AankoopDAO aankoopDAO;

    @Mock
    private ReviewDAO reviewDAO;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private ReviewPaginaController reviewPaginaController;

    private Boek boek;
    private Gebruiker gebruiker;
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        boek = new Boek();
        boek.setIsbn("1234567890");

        gebruiker = new Gebruiker();
        gebruiker.setEmail("test@example.com");

        authentication = new UsernamePasswordAuthenticationToken(
                "test@example.com",
                "password",
                List.of()
        );
    }

    @Test
    void showReviewForm() {
        when(boekDAO.findById("1234567890"))
                .thenReturn(Optional.of(boek));
        when(gebruikerDAO.findByEmail("test@example.com"))
                .thenReturn(Optional.of(gebruiker));
        when(aankoopDAO.existsByGebruikerAndBoek(gebruiker, boek))
                .thenReturn(true);
        when(reviewDAO.findByGebruikerAndBoek(gebruiker, boek))
                .thenReturn(Optional.empty());

        String resultaat = reviewPaginaController.showReviewForm(
                "1234567890",
                authentication,
                model,
                new RedirectAttributesModelMap()
        );

        assertEquals("review", resultaat);

        verify(model).addAttribute("boek", boek);
        verify(model).addAttribute(eq("review"), any(Review.class));
    }

    @Test
    void denyReviewForUnpurchasedBook() {
        when(boekDAO.findById("1234567890"))
                .thenReturn(Optional.of(boek));
        when(gebruikerDAO.findByEmail("test@example.com"))
                .thenReturn(Optional.of(gebruiker));
        when(aankoopDAO.existsByGebruikerAndBoek(gebruiker, boek))
                .thenReturn(false);

        RedirectAttributesModelMap attributes =
                new RedirectAttributesModelMap();

        String resultaat = reviewPaginaController.showReviewForm(
                "1234567890",
                authentication,
                model,
                attributes
        );

        assertEquals("redirect:/aankopen", resultaat);
        assertNotNull(attributes.getFlashAttributes().get("foutmelding"));

        verify(reviewDAO, never())
                .save(any(Review.class));
    }

    @Test
    void saveReview() {
        when(boekDAO.findById("1234567890"))
                .thenReturn(Optional.of(boek));
        when(gebruikerDAO.findByEmail("test@example.com"))
                .thenReturn(Optional.of(gebruiker));
        when(aankoopDAO.existsByGebruikerAndBoek(gebruiker, boek))
                .thenReturn(true);
        when(reviewDAO.findByGebruikerAndBoek(gebruiker, boek))
                .thenReturn(Optional.empty());
        when(bindingResult.hasErrors()).thenReturn(false);

        Review review = new Review();
        review.setDescription(
                "Een heel goed boek dat ik met plezier heb gelezen."
        );
        review.setScore(5);

        RedirectAttributesModelMap attributes =
                new RedirectAttributesModelMap();

        String resultaat = reviewPaginaController.saveReview(
                "1234567890",
                review,
                bindingResult,
                authentication,
                model,
                attributes
        );

        assertEquals("redirect:/aankopen", resultaat);
        assertEquals(boek, review.getBoek());
        assertEquals(gebruiker, review.getGebruiker());
        assertEquals(LocalDate.now(), review.getPostDate());
        assertNotNull(attributes.getFlashAttributes().get("bevestiging"));

        verify(reviewDAO).save(review);
    }

    @Test
    void denyMultipleReviewsForSameBook() {
        when(boekDAO.findById("1234567890"))
                .thenReturn(Optional.of(boek));
        when(gebruikerDAO.findByEmail("test@example.com"))
                .thenReturn(Optional.of(gebruiker));
        when(aankoopDAO.existsByGebruikerAndBoek(gebruiker, boek))
                .thenReturn(true);
        when(reviewDAO.findByGebruikerAndBoek(gebruiker, boek))
                .thenReturn(Optional.of(new Review()));

        RedirectAttributesModelMap attributes =
                new RedirectAttributesModelMap();

        String resultaat = reviewPaginaController.saveReview(
                "1234567890",
                new Review(),
                bindingResult,
                authentication,
                model,
                attributes
        );

        assertEquals("redirect:/aankopen", resultaat);
        assertNotNull(attributes.getFlashAttributes().get("foutmelding"));

        verify(reviewDAO, never())
                .save(any(Review.class));
    }

}
