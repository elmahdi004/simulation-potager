package com.example.simulation_potager.Service;

import com.example.simulation_potager.Entity.Parcelle;
import com.example.simulation_potager.Repository.ParcelleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParcelleService {
    @Autowired
    private ParcelleRepository parcelleRepository;

    public List<Parcelle> getAllParcelles() {
        return parcelleRepository.findAll();
    }

    public Optional<Parcelle> getParcelleById(Long id) {
        return parcelleRepository.findById(id);
    }

    public Parcelle saveParcelle(Parcelle parcelle) {
        return parcelleRepository.save(parcelle);
    }

    public void deleteParcelle(Long id) {
        parcelleRepository.deleteById(id);
    }
}