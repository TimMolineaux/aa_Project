package com.example.aa_project.Controller;

import com.example.aa_project.Datasource.AankoopDAO;
import com.example.aa_project.Datasource.BoekDAO;
import com.example.aa_project.Datasource.GebruikerDAO;
import com.example.aa_project.Datasource.ReviewDAO;
import com.example.aa_project.Model.Boek;
import com.example.aa_project.Model.Gebruiker;
import com.example.aa_project.Model.Review;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
public class ReviewPaginaController {

    private BoekDAO boekDAO;
    private GebruikerDAO gebruikerDAO;
    private AankoopDAO aankoopDAO;
    private ReviewDAO reviewDAO;

    ReviewPaginaController(BoekDAO boekDAO, GebruikerDAO gebruikerDAO, AankoopDAO aankoopDAO, ReviewDAO reviewDAO){

        this.boekDAO = boekDAO;
        this.gebruikerDAO = gebruikerDAO;
        this.aankoopDAO = aankoopDAO;
        this.reviewDAO = reviewDAO;

    }

    @GetMapping("/reviews/nieuw/{isbn}")
    public String toonReviewFormulier(
            @PathVariable String isbn,
            Authentication authentication,
            Model model,
            RedirectAttributes redirectAttributes) {

        Boek boek = boekDAO.findById(isbn).orElse(null);

        if (boek == null) {
            return "redirect:/";
        }

        Gebruiker gebruiker = gebruikerDAO
                .findByEmail(authentication.getName())
                .orElseThrow();

        if (!aankoopDAO.existsByGebruikerAndBoek(gebruiker, boek)) {
            redirectAttributes.addFlashAttribute(
                    "foutmelding",
                    "Je kunt alleen reviews schrijven voor boeken die je gekocht hebt."
            );
            return "redirect:/aankopen";
        }

        Review bestaandeReview = reviewDAO
                .findByGebruikerAndBoek(gebruiker, boek)
                .orElse(null);

        if (bestaandeReview != null) {
            redirectAttributes.addFlashAttribute(
                    "foutmelding",
                    "Je hebt voor dit boek al een review geschreven."
            );
            return "redirect:/aankopen";
        }

        Review review = new Review();
        review.setBoek(boek);

        model.addAttribute("boek", boek);
        model.addAttribute("review", review);

        return "review";
    }


    @PostMapping("/reviews/nieuw/{isbn}")
    public String bewaarReview(
            @PathVariable String isbn,
            @Valid Review review,
            BindingResult bindingResult,
            Authentication authentication,
            Model model,
            RedirectAttributes redirectAttributes) {

        Boek boek = boekDAO.findById(isbn).orElse(null);

        if (boek == null) {
            return "redirect:/";
        }

        Gebruiker gebruiker = gebruikerDAO
                .findByEmail(authentication.getName())
                .orElseThrow();

        if (!aankoopDAO.existsByGebruikerAndBoek(gebruiker, boek)) {
            redirectAttributes.addFlashAttribute(
                    "foutmelding",
                    "Je kunt alleen reviews schrijven voor boeken die je gekocht hebt."
            );
            return "redirect:/profiel/aankopen";
        }

        if (reviewDAO.findByGebruikerAndBoek(gebruiker, boek).isPresent()) {
            redirectAttributes.addFlashAttribute(
                    "foutmelding",
                    "Je hebt voor dit boek al een review geschreven."
            );
            return "redirect:/profiel/aankopen";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("boek", boek);
            return "review";
        }

        review.setReviewId(0);
        review.setBoek(boek);
        review.setGebruiker(gebruiker);
        review.setPostDate(LocalDate.now());

        reviewDAO.save(review);

        redirectAttributes.addFlashAttribute(
                "bevestiging",
                "Je review is succesvol geplaatst!"
        );

        return "redirect:/aankopen";
    }

}
