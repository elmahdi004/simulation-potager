package com.example.simulation_potager.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Parcelle {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private int x;
        private int y;

        private double humidite;

        // Une parcelle peut avoir plusieurs plantes
        @OneToMany(mappedBy = "parcelle", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<Plante> plantes;

        // Une parcelle peut avoir plusieurs insectes
        @OneToMany(mappedBy = "parcelle", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<Insecte> insectes;

        // Une parcelle peut avoir un seul dispositif
        @OneToOne(mappedBy = "parcelle", cascade = CascadeType.ALL)
        private Dispositif dispositif;

        // Getters et Setters

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public double getHumidite() {
            return humidite;
        }

        public void setHumidite(double humidite) {
            this.humidite = humidite;
        }

        public List<Plante> getPlantes() {
            return plantes;
        }

        public void setPlantes(List<Plante> plantes) {
            this.plantes = plantes;
        }

        public List<Insecte> getInsectes() {
            return insectes;
        }

        public void setInsectes(List<Insecte> insectes) {
            this.insectes = insectes;
        }

        public Dispositif getDispositif() {
            return dispositif;
        }

        public void setDispositif(Dispositif dispositif) {
            this.dispositif = dispositif;
        }
    }


