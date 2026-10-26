package com.example.aa_project.Model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Aankopen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int aankoopId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private Gebruiker gebruiker;

    @ManyToOne
    @JoinColumn(name = "isbn", nullable = false)
    private Boek boek;

    private int amount;

    private LocalDateTime purchaseDate;

    public Aankopen(){

    }

    public Aankopen(int aankoopId, Gebruiker gebruiker, Boek boek, int amount, LocalDateTime purchaseDate) {
        this.aankoopId = aankoopId;
        this.gebruiker = gebruiker;
        this.boek = boek;
        this.amount = amount;
        this.purchaseDate = purchaseDate;
    }

    public int getAankoopId() {
        return aankoopId;
    }

    public void setAankoopId(int aankoopId) {
        this.aankoopId = aankoopId;
    }

    public Gebruiker getGebruiker() {
        return gebruiker;
    }

    public void setGebruiker(Gebruiker gebruiker) {
        this.gebruiker = gebruiker;
    }

    public Boek getBoek() {
        return boek;
    }

    public void setBoek(Boek boek) {
        this.boek = boek;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}
