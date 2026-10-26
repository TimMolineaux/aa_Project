package com.example.aa_project.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
public class Reservatie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reservatieId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private Gebruiker gebruiker;

    @ManyToOne
    @JoinColumn(name = "isbn", nullable = false)
    private  Boek boek;

    @Min(1)
    private int ammount;

    @NotNull
    private LocalDate pickupDate;

    private boolean status = false;

    public Reservatie() {

    }

    public Reservatie(int reservatieId, Gebruiker gebruiker, Boek boek, int ammount, LocalDate pickupDate, boolean status) {
        this.reservatieId = reservatieId;
        this.gebruiker = gebruiker;
        this.boek = boek;
        this.ammount = ammount;
        this.pickupDate = pickupDate;
        this.status = status;
    }

    public int getReservatieId() {
        return reservatieId;
    }

    public void setReservatieId(int reservatieId) {
        this.reservatieId = reservatieId;
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

    public int getAmmount() {
        return ammount;
    }

    public void setAmmount(int ammount) {
        this.ammount = ammount;
    }

    public LocalDate getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(LocalDate pickupDate) {
        this.pickupDate = pickupDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
