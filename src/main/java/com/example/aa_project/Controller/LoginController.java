package com.example.aa_project.Controller;

import com.example.aa_project.Datasource.GebruikerDAO;
import com.example.aa_project.Model.Gebruiker;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class LoginController {

    private GebruikerDAO gebruikerDAO;
    private PasswordEncoder passwordEncoder;

    public LoginController(GebruikerDAO gebruikerDAO, PasswordEncoder passwordEncoder) {
        this.gebruikerDAO = gebruikerDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/registreren")
    public String showRegisterPage(Model model) {
        model.addAttribute("gebruiker", new Gebruiker());
        return "registreren";
    }

    @PostMapping("/registreren")
    public String register(
            @Valid @ModelAttribute("gebruiker") Gebruiker gebruiker,BindingResult bindingResult,Model model, HttpServletRequest request,
            HttpServletResponse response) {

        if (bindingResult.hasErrors()) {
            return "registreren";
        }

        if (gebruikerDAO.existsByEmail(gebruiker.getEmail())) {
            model.addAttribute("foutmelding", "Dit e-mailadres is al in gebruik.");
            return "registreren";
        }

        gebruiker.setPassword(
                passwordEncoder.encode(gebruiker.getPassword())
        );

        gebruikerDAO.save(gebruiker);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        gebruiker.getEmail(),
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_USER"))
                );

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        new HttpSessionSecurityContextRepository()
                .saveContext(context, request, response);

        return "redirect:/";
    }




}
