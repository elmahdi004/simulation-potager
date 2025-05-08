package com.example.simulation_potager.Service;

import com.example.simulation_potager.Entity.Dispositif;
import com.example.simulation_potager.Repository.DispositifRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DispositifService {
    @Autowired
    private DispositifRepository dispositifRepository;

    public List<Dispositif> getAllDispositifs() {
        return dispositifRepository.findAll();
    }

    public Optional<Dispositif> getDispositifById(Long id) {
        return dispositifRepository.findById(id);
    }

    public Dispositif saveDispositif(Dispositif dispositif) {
        return dispositifRepository.save(dispositif);
    }

    public void deleteDispositif(Long id) {
        dispositifRepository.deleteById(id);
    }
}