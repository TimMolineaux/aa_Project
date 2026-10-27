package com.example.aa_project.Security;

import com.example.aa_project.Datasource.GebruikerDAO;
import com.example.aa_project.Model.Gebruiker;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GebruikerService implements UserDetailsService {

    private final GebruikerDAO gebruikerDAO;

    public GebruikerService(GebruikerDAO gebruikerDAO) {
        this.gebruikerDAO = gebruikerDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Gebruiker gebruiker = gebruikerDAO.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Gebruiker niet gevonden"
                        )
                );

        return User.withUsername(gebruiker.getEmail())
                .password(gebruiker.getPassword())
                .roles("USER")
                .build();
    }

}
