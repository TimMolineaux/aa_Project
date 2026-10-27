package com.example.aa_project.Controller;

import com.example.aa_project.Datasource.AankoopDAO;
import com.example.aa_project.Datasource.GebruikerDAO;
import com.example.aa_project.Datasource.ReservatieDAO;
import com.example.aa_project.Model.Gebruiker;
import com.example.aa_project.Model.Reservatie;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProfielController {

    private GebruikerDAO gebruikerDAO;
    private ReservatieDAO reservatieDAO;
    private AankoopDAO aankoopDAO;

    public ProfielController (GebruikerDAO gebruikerDAO, ReservatieDAO reservatieDAO, AankoopDAO aankoopDAO){

        this.gebruikerDAO = gebruikerDAO;
        this.reservatieDAO = reservatieDAO;
        this.aankoopDAO = aankoopDAO;

    }

    @GetMapping("/reservaties")
    public String showReservations(
            Authentication authentication,
            Model model) {

        Gebruiker gebruiker = gebruikerDAO
                .findByEmail(authentication.getName())
                .orElseThrow();

        model.addAttribute(
                "reservaties",
                reservatieDAO.findByGebruikerAndStatus(
                        gebruiker, false
                )
        );

        return "reservaties";
    }

    @GetMapping("/aankopen")
    public String showPurchases(
            Authentication authentication,
            Model model) {

        Gebruiker gebruiker = gebruikerDAO
                .findByEmail(authentication.getName())
                .orElseThrow();

        model.addAttribute(
                "aankopen",
                aankoopDAO.findByGebruiker(gebruiker)
        );

        return "aankopen";
    }


    @PostMapping("/reservaties/{id}/voltooien")
    public String completeReservation(
            @PathVariable int id,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

        Gebruiker gebruiker = gebruikerDAO
                .findByEmail(authentication.getName())
                .orElseThrow();

        Reservatie reservatie = reservatieDAO
                .findById(id)
                .orElse(null);

        if (reservatie == null) {
            redirectAttributes.addFlashAttribute(
                    "foutmelding",
                    "Deze reservatie bestaat niet."
            );
            return "redirect:/reservaties";
        }

        if (reservatie.getGebruiker().getUserId()
                != gebruiker.getUserId()) {

            redirectAttributes.addFlashAttribute(
                    "foutmelding",
                    "Je mag deze reservatie niet voltooien."
            );
            return "redirect:/reservaties";
        }

        if (reservatie.isStatus()) {
            redirectAttributes.addFlashAttribute(
                    "foutmelding",
                    "Deze reservatie is al voltooid."
            );
            return "redirect:/reservaties";
        }

        reservatie.setStatus(true);
        reservatieDAO.save(reservatie);

        redirectAttributes.addFlashAttribute(
                "bevestiging",
                "Je reservatie is succesvol voltooid!"
        );

        return "redirect:/reservaties";
    }

}
