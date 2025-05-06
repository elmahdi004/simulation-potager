package com.example.simulation_potager.Repository;

import com.example.simulation_potager.Entity.Plante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanteRepository extends JpaRepository<Plante, Long> {

}
