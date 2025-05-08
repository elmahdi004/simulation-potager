package com.example.simulation_potager.Controller;

import com.example.simulation_potager.Entity.Parcelle;
import com.example.simulation_potager.Service.ParcelleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/parcelles")
@CrossOrigin(origins = "*")
public class ParcelleController {
    @Autowired
    private ParcelleService parcelleService;

    @GetMapping
    public List<Parcelle> getAllParcelles() {
        return parcelleService.getAllParcelles();
    }

    @GetMapping("/{id}")
    public Optional<Parcelle> getParcelleById(@PathVariable Long id) {
        return parcelleService.getParcelleById(id);
    }

    @PostMapping
    public Parcelle createParcelle(@RequestBody Parcelle parcelle) {
        return parcelleService.saveParcelle(parcelle);
    }

    @PutMapping("/{id}")
    public Parcelle updateParcelle(@PathVariable Long id, @RequestBody Parcelle parcelle) {
        parcelle.setId(id);
        return parcelleService.saveParcelle(parcelle);
    }

    @DeleteMapping("/{id}")
    public void deleteParcelle(@PathVariable Long id) {
        parcelleService.deleteParcelle(id);
    }
}