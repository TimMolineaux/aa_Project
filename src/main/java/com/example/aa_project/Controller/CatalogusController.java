package com.example.aa_project.Controller;

import com.example.aa_project.Datasource.BoekDAO;
import com.example.aa_project.Model.Boek;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CatalogusController {

    private BoekDAO boekDAO;

    public CatalogusController (BoekDAO boekDAO){

        this.boekDAO = boekDAO;

    }

    @GetMapping("/")
    public String showCatalog(Model model) {
        model.addAttribute("boeken", boekDAO.findAll());
        return "index";
    }

    @GetMapping("/boeken/{isbn}")
    public String toonBoek(@PathVariable String isbn, Model model) {

        Boek boek = boekDAO.findById(isbn).orElse(null);

        if (boek == null) {
            return "redirect:/";
        }

        model.addAttribute("boek", boek);
        return "detailpagina";
    }

}
