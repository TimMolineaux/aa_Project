package com.example.aa_project.Controller;

import com.example.aa_project.Datasource.AankoopDAO;
import com.example.aa_project.Datasource.BoekDAO;
import com.example.aa_project.Datasource.GebruikerDAO;
import com.example.aa_project.Datasource.ReservatieDAO;
import com.example.aa_project.Model.Aankoop;
import com.example.aa_project.Model.Boek;
import com.example.aa_project.Model.Gebruiker;
import com.example.aa_project.Model.Reservatie;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
public class TransactieController {

    private BoekDAO boekDAO;
    private GebruikerDAO gebruikerDAO;
    private ReservatieDAO reservatieDAO;
    private AankoopDAO aankoopDAO;

    public TransactieController(BoekDAO boekDAO,GebruikerDAO gebruikerDAO,ReservatieDAO reservatieDAO, AankoopDAO aankoopDAO){

        this.boekDAO = boekDAO;
        this.gebruikerDAO = gebruikerDAO;
        this.reservatieDAO = reservatieDAO;
        this.aankoopDAO = aankoopDAO;

    }

    @PostMapping("/boeken/{isbn}/reserveren")
    @Transactional
    public String reserveerBoek(
            @PathVariable String isbn,
            @RequestParam int aantal,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

        Boek boek = boekDAO.findById(isbn).orElse(null);

        if (boek == null) {
            redirectAttributes.addFlashAttribute(
                    "foutmelding",
                    "Dit boek bestaat niet."
            );
            return "redirect:/";
        }

        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        if (aantal < 1) {
            redirectAttributes.addFlashAttribute(
                    "foutmelding",
                    "Je moet minstens één exemplaar reserveren."
            );
            return "redirect:/boeken/" + isbn;
        }

        if (aantal > boek.getVoorraad()) {
            redirectAttributes.addFlashAttribute(
                    "foutmelding",
                    "Onvoldoende voorraad. Er zijn nog "
                            + boek.getVoorraad()
                            + " exemplaren beschikbaar."
            );
            return "redirect:/boeken/" + isbn;
        }

        Gebruiker gebruiker = gebruikerDAO
                .findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Ingelogde gebruiker niet gevonden."
                        )
                );

        Reservatie reservatie = new Reservatie();
        reservatie.setGebruiker(gebruiker);
        reservatie.setBoek(boek);
        reservatie.setAmount(aantal);
        reservatie.setPickupDate(LocalDate.now());
        reservatie.setStatus(false);

        Aankoop aankoop = new Aankoop();
        aankoop.setGebruiker(gebruiker);
        aankoop.setBoek(boek);
        aankoop.setAmount(aantal);
        aankoop.setPurchaseDate(LocalDateTime.now());

        boek.setVoorraad(boek.getVoorraad() - aantal);

        boekDAO.save(boek);
        reservatieDAO.save(reservatie);
        aankoopDAO.save(aankoop);

        redirectAttributes.addFlashAttribute(
                "bevestiging",
                "Je reservatie is bevestigd! Je kunt je boeken vandaag afhalen."
        );

        return "redirect:/boeken/" + isbn;
    }

}
