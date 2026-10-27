package com.example.aa_project.Controller;

import com.example.aa_project.Datasource.BoekDAO;
import com.example.aa_project.Datasource.ReviewDAO;
import com.example.aa_project.Model.Boek;
import com.example.aa_project.Model.Review;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class CatalogusController {

    private BoekDAO boekDAO;
    private ReviewDAO reviewDAO;

    public CatalogusController (BoekDAO boekDAO, ReviewDAO reviewDAO){

        this.boekDAO = boekDAO;
        this.reviewDAO = reviewDAO;

    }

    @GetMapping("/")
    public String showCatalog(Model model) {
        model.addAttribute("boeken", boekDAO.findAll());
        return "index";
    }

    @GetMapping("/boeken/{isbn}")
    public String toonBoek(
            @PathVariable String isbn,
            Model model) {

        Boek boek = boekDAO.findById(isbn).orElse(null);

        if (boek == null) {
            return "redirect:/";
        }

        List<Review> reviews = reviewDAO.findByBoek(boek);

        model.addAttribute("boek", boek);
        model.addAttribute("reviews", reviews);

        return "detailpagina";
    }

}
