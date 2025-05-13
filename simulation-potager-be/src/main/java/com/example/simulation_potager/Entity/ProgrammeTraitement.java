package com.example.simulation_potager.Entity;

import jakarta.persistence.*;

@Entity
public class ProgrammeTraitement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int startStep; // When to start
    private int duration;  // How many steps to stay active
    private String type;   // "Arrosage", "Engrais", "Insecticide"

    @ManyToOne
    @JoinColumn(name = "dispositif_id")
    private Dispositif dispositif;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getStartStep() { return startStep; }
    public void setStartStep(int startStep) { this.startStep = startStep; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Dispositif getDispositif() { return dispositif; }
    public void setDispositif(Dispositif dispositif) { this.dispositif = dispositif; }
} 