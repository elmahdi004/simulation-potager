package com.example.simulation_potager.Service;

import com.example.simulation_potager.Entity.Insecte;
import com.example.simulation_potager.Repository.InsecteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InsecteService {
    @Autowired
    private InsecteRepository insecteRepository;

    public List<Insecte> getAllInsectes() {
        return insecteRepository.findAll();
    }

    public Optional<Insecte> getInsecteById(Long id) {
        return insecteRepository.findById(id);
    }

    public Insecte saveInsecte(Insecte insecte) {
        return insecteRepository.save(insecte);
    }

    public void deleteInsecte(Long id) {
        insecteRepository.deleteById(id);
    }
}