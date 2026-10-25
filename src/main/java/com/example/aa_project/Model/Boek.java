package com.example.aa_project.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Boek {

    @Id
    private String isbn;

    private String titel;

    private String auteurs;

    private String beschrijving;

    private String genre;

    private Float prijs;

    private int voorraad;

    public Boek(){

    }

    public Boek(String isbn, String titel, String auteurs, int voorraad, Float prijs, String genre, String beschrijving) {
        this.isbn = isbn;
        this.titel = titel;
        this.auteurs = auteurs;
        this.voorraad = voorraad;
        this.prijs = prijs;
        this.genre = genre;
        this.beschrijving = beschrijving;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getAuteurs() {
        return auteurs;
    }

    public void setAuteurs(String auteurs) {
        this.auteurs = auteurs;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Float getPrijs() {
        return prijs;
    }

    public void setPrijs(Float prijs) {
        this.prijs = prijs;
    }

    public int getVoorraad() {
        return voorraad;
    }

    public void setVoorraad(int voorraad) {
        this.voorraad = voorraad;
    }
}
