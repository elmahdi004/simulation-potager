package com.example.simulation_potager.Controller;

import com.example.simulation_potager.Entity.Plante;
import com.example.simulation_potager.Service.PlanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plantes")
public class PlanteController {
    @Autowired
    PlanteService planteService;

    @GetMapping
    public List<Plante> getPlantes() {
        return planteService.getToutesLesPlantes();
    }

    @PostMapping
    public Plante ajouterPlante(@RequestBody Plante plante) {
        return planteService.ajouterPlante(plante);
    }

    @DeleteMapping("/{id}")
    public void supprimerPlante(@PathVariable Long id) {
        planteService.supprimerPlante(id);
    }
}
