package com.example.simulation_potager.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
public class Insecte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String espece;

    private String sexe;

    private int sante; // entre 0 et 10

    private double mobilite; // probabilité de déplacement (0.0 à 1.0)

    private double resistanceInsecticide; // probabilité de survivre à l'insecticide (0.0 à 1.0)

    private int stepsWithoutFeeding = 0;

    // Lien avec la parcelle où se trouve l'insecte
    @ManyToOne
    @JoinColumn(name = "parcelle_id")
    @JsonIgnoreProperties(value = "insectes")
    private Parcelle parcelle;

    // Getters & Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEspece() {
        return espece;
    }

    public void setEspece(String espece) {
        this.espece = espece;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public int getSante() {
        return sante;
    }

    public void setSante(int sante) {
        this.sante = sante;
    }

    public double getMobilite() {
        return mobilite;
    }

    public void setMobilite(double mobilite) {
        this.mobilite = mobilite;
    }

    public double getResistanceInsecticide() {
        return resistanceInsecticide;
    }

    public void setResistanceInsecticide(double resistanceInsecticide) {
        this.resistanceInsecticide = resistanceInsecticide;
    }

    public Parcelle getParcelle() {
        return parcelle;
    }

    public void setParcelle(Parcelle parcelle) {
        this.parcelle = parcelle;
    }

    public int getStepsWithoutFeeding() {
        return stepsWithoutFeeding;
    }

    public void setStepsWithoutFeeding(int stepsWithoutFeeding) {
        this.stepsWithoutFeeding = stepsWithoutFeeding;
    }
}
