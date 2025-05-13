package com.example.simulation_potager.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Dispositif {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Rayon d'action du dispositif pour les traitements
    private int rayon;

    // Relation OneToOne vers la parcelle concernée
    @OneToOne
    @JoinColumn(name = "parcelle_id", unique = true)
    @JsonIgnoreProperties(value = "dispositif")
    private Parcelle parcelle;

    // Liste des programmes d'activation
    @OneToMany(mappedBy = "dispositif", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = "dispositif")
    private List<ProgrammeTraitement> programmes;

    // Getters et Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRayon() {
        return rayon;
    }

    public void setRayon(int rayon) {
        this.rayon = rayon;
    }

    public Parcelle getParcelle() {
        return parcelle;
    }

    public void setParcelle(Parcelle parcelle) {
        this.parcelle = parcelle;
    }

    public List<ProgrammeTraitement> getProgrammes() {
        return programmes;
    }

    public void setProgrammes(List<ProgrammeTraitement> programmes) {
        this.programmes = programmes;
    }
}

