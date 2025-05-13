package com.example.simulation_potager.Service;

import com.example.simulation_potager.Entity.Plante;
import com.example.simulation_potager.Repository.PlanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanteService {
    @Autowired
    PlanteRepository planteRepository;
    public List<Plante> getToutesLesPlantes() {
        return planteRepository.findAll();
    }

    public Plante ajouterPlante(Plante plante) {
        return planteRepository.save(plante);
    }

    public void supprimerPlante(Long id) {
        planteRepository.deleteById(id);
    }

    public Optional<Plante> getPlanteById(Long id){
        return planteRepository.findById(id);
    }
}
