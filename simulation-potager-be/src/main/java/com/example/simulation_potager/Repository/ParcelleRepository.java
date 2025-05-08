package com.example.simulation_potager.Repository;

import com.example.simulation_potager.Entity.Parcelle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParcelleRepository extends JpaRepository<Parcelle, Long> {
}