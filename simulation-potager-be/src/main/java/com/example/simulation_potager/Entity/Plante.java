package com.example.simulation_potager.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
@Entity
public class Plante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String espece;
    private int age;
    private int ageMaturite;
    private boolean mature;
    private boolean drageonnante;
    private double tauxColonisation;
    @ManyToOne
    @JoinColumn(name = "parcelle_id")
    @JsonIgnoreProperties(value = "plantes")
    private Parcelle parcelle;

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAgeMaturite() {
        return ageMaturite;
    }

    public void setAgeMaturite(int ageMaturite) {
        this.ageMaturite = ageMaturite;
    }

    public boolean isMature() {
        return mature;
    }

    public void setMature(boolean mature) {
        this.mature = mature;
    }

    public boolean isDrageonnante() {
        return drageonnante;
    }

    public void setDrageonnante(boolean drageonnante) {
        this.drageonnante = drageonnante;
    }

    public double getTauxColonisation() {
        return tauxColonisation;
    }

    public void setTauxColonisation(double tauxColonisation) {
        this.tauxColonisation = tauxColonisation;
    }

    public Parcelle getParcelle() {
        return parcelle;
    }

    public void setParcelle(Parcelle parcelle) {
        this.parcelle = parcelle;
    }
}
