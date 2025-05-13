package com.example.simulation_potager.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.simulation_potager.Service.SimulationService;

@RestController
@RequestMapping("/api/simulation")
@CrossOrigin(origins = "*")
public class SimulationController {
    @Autowired
    private SimulationService simulationService;

    @PostMapping("/step")
    public void step() {
        simulationService.step();
    }
} 