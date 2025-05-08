package com.example.simulation_potager.Controller;

import com.example.simulation_potager.Entity.Dispositif;
import com.example.simulation_potager.Service.DispositifService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/dispositifs")
@CrossOrigin(origins = "*")
public class DispositifController {
    @Autowired
    private DispositifService dispositifService;

    @GetMapping
    public List<Dispositif> getAllDispositifs() {
        return dispositifService.getAllDispositifs();
    }

    @GetMapping("/{id}")
    public Optional<Dispositif> getDispositifById(@PathVariable Long id) {
        return dispositifService.getDispositifById(id);
    }

    @PostMapping
    public Dispositif createDispositif(@RequestBody Dispositif dispositif) {
        return dispositifService.saveDispositif(dispositif);
    }

    @PutMapping("/{id}")
    public Dispositif updateDispositif(@PathVariable Long id, @RequestBody Dispositif dispositif) {
        dispositif.setId(id);
        return dispositifService.saveDispositif(dispositif);
    }

    @DeleteMapping("/{id}")
    public void deleteDispositif(@PathVariable Long id) {
        dispositifService.deleteDispositif(id);
    }
}