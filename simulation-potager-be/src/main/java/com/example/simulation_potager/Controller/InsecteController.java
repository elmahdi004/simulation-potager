package com.example.simulation_potager.Controller;

import com.example.simulation_potager.Entity.Insecte;
import com.example.simulation_potager.Service.InsecteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/insectes")
@CrossOrigin(origins = "*")
public class InsecteController {
    @Autowired
    private InsecteService insecteService;

    @GetMapping
    public List<Insecte> getAllInsectes() {
        return insecteService.getAllInsectes();
    }

    @GetMapping("/{id}")
    public Optional<Insecte> getInsecteById(@PathVariable Long id) {
        return insecteService.getInsecteById(id);
    }

    @PostMapping
    public Insecte createInsecte(@RequestBody Insecte insecte) {
        return insecteService.saveInsecte(insecte);
    }

    @PutMapping("/{id}")
    public Insecte updateInsecte(@PathVariable Long id, @RequestBody Insecte insecte) {
        insecte.setId(id);
        return insecteService.saveInsecte(insecte);
    }

    @DeleteMapping("/{id}")
    public void deleteInsecte(@PathVariable Long id) {
        insecteService.deleteInsecte(id);
    }
}