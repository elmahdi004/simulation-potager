package com.example.simulation_potager.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.simulation_potager.Entity.Plante;
import com.example.simulation_potager.Repository.PlanteRepository;
import com.example.simulation_potager.Entity.Parcelle;
import com.example.simulation_potager.Repository.ParcelleRepository;
import com.example.simulation_potager.Entity.Insecte;
import com.example.simulation_potager.Repository.InsecteRepository;
import com.example.simulation_potager.Entity.Dispositif;
import com.example.simulation_potager.Repository.DispositifRepository;
import com.example.simulation_potager.Entity.ProgrammeTraitement;

import java.util.List;

@Service
public class SimulationService {
    @Autowired
    private PlanteRepository planteRepository;
    @Autowired
    private ParcelleRepository parcelleRepository;
    @Autowired
    private InsecteRepository insecteRepository;
    @Autowired
    private DispositifRepository dispositifRepository;

    private int currentStep = 0;

    public void step() {
        // 1. Age plants and handle maturity
        List<Plante> plantes = planteRepository.findAll();
        for (Plante plante : plantes) {
            plante.setAge(plante.getAge() + 1);
            if (plante.getAge() >= plante.getAgeMaturite()) {
                plante.setMature(true);
            }
            // Fruiting logic: if mature, produce fruits
            if (plante.isMature()) {
                plante.setFruits(plante.getFruits() + 1);
            }
            planteRepository.save(plante);
        }

        // 2. Drageonnantes (spreading) plant colonization
        List<Parcelle> parcelles = parcelleRepository.findAll();
        for (Parcelle parcelle : parcelles) {
            if (parcelle.getPlantes() == null) continue;
            for (Plante plante : parcelle.getPlantes()) {
                if (plante.isMature() && plante.isDrageonnante()) {
                    // Check 4 neighbors (up, down, left, right)
                    System.out.println("The Plantes is Mature");
                    int[][] directions = {{0,1},{1,0},{0,-1},{-1,0}};
                    for (int[] dir : directions) {
                        int nx = parcelle.getX() + dir[0];
                        int ny = parcelle.getY() + dir[1];
                        Parcelle neighbor = parcelles.stream()
                            .filter(p -> p.getX() == nx && p.getY() == ny)
                            .findFirst()
                            .orElse(null);
                        if (neighbor != null && (neighbor.getPlantes() == null || neighbor.getPlantes().isEmpty())) {
                            System.out.println("He does not have any plantes");
                            // Try to colonize
                            if (Math.random() < plante.getTauxColonisation()) {
                                System.out.println("i will create a new plante");
                                Plante newPlant = new Plante();
                                newPlant.setEspece(plante.getEspece());
                                newPlant.setAge(0);
                                newPlant.setAgeMaturite(plante.getAgeMaturite());
                                newPlant.setMature(false);
                                newPlant.setDrageonnante(plante.isDrageonnante());
                                newPlant.setTauxColonisation(plante.getTauxColonisation());
                                newPlant.setParcelle(neighbor);
                                planteRepository.save(newPlant);
                            }
                        }
                    }
                }
            }
        }

        // 3. Insect behavior: feeding, health, death, movement
        List<Insecte> insectes = insecteRepository.findAll();
        for (Insecte insecte : insectes) {
            Parcelle currentParcelle = insecte.getParcelle();
            boolean fed = false;

            // Feeding: if there is at least one plant on the current plot
            if (currentParcelle != null && currentParcelle.getPlantes() != null && !currentParcelle.getPlantes().isEmpty()) {
                fed = true;
                insecte.setSante(Math.min(10, insecte.getSante() + 1)); // Gain health, max 10
                insecte.setStepsWithoutFeeding(0); // Reset starvation counter
            } else {
                // Not fed
                insecte.setSante(insecte.getSante() - 1); // Lose health
                insecte.setStepsWithoutFeeding(insecte.getStepsWithoutFeeding() + 1);
            }

            // Death: if health <= 0 or not fed for 5 steps
            if (insecte.getSante() <= 0 || insecte.getStepsWithoutFeeding() >= 5) {
                insecteRepository.delete(insecte);
                continue;
            }

            // Movement: move to a random neighboring plot based on mobility
            if (Math.random() < insecte.getMobilite()) { // mobilite should be a value between 0 and 1
                int[][] directions = {{0,1},{1,0},{0,-1},{-1,0}};
                int dirIdx = (int)(Math.random() * 4);
                int nx = currentParcelle.getX() + directions[dirIdx][0];
                int ny = currentParcelle.getY() + directions[dirIdx][1];
                Parcelle neighbor = parcelles.stream()
                    .filter(p -> p.getX() == nx && p.getY() == ny)
                    .findFirst()
                    .orElse(null);
                if (neighbor != null) {
                    insecte.setParcelle(neighbor);
                }
            }

            insecteRepository.save(insecte);
        }

        // 4. Treatment devices: apply only if active in current step
        List<Dispositif> dispositifs = dispositifRepository.findAll();
        for (Dispositif dispositif : dispositifs) {
            boolean isActive = false;
            String activeType = null;
            if (dispositif.getProgrammes() != null) {
                for (ProgrammeTraitement prog : dispositif.getProgrammes()) {
                    int start = prog.getStartStep();
                    int end = start + prog.getDuration();
                    if (currentStep >= start && currentStep < end) {
                        isActive = true;
                        activeType = prog.getType();
                        break;
                    }
                }
            }
            if (!isActive) continue;

            Parcelle center = dispositif.getParcelle();
            int rayon = dispositif.getRayon();

            for (Parcelle parcelle : parcelles) {
                int dx = Math.abs(parcelle.getX() - center.getX());
                int dy = Math.abs(parcelle.getY() - center.getY());
                if (dx + dy <= rayon) {
                    if ("Arrosage".equalsIgnoreCase(activeType)) {
                        parcelle.setHumidite(Math.min(1.0, parcelle.getHumidite() + 0.3));
                        parcelleRepository.save(parcelle);
                    }
                    if ("Insecticide".equalsIgnoreCase(activeType)) {
                        if (parcelle.getInsectes() != null) {
                            for (Insecte insecte : parcelle.getInsectes()) {
                                // Insect has a chance to survive based on resistance
                                if (Math.random() > insecte.getResistanceInsecticide()) {
                                    insecteRepository.delete(insecte);
                                } else {
                                    // Optionally, reduce health instead of deleting
                                    insecte.setSante(Math.max(0, insecte.getSante() - 5));
                                    insecteRepository.save(insecte);
                                }
                            }
                        }
                    }
                    if ("Engrais".equalsIgnoreCase(activeType)) {
                        if (parcelle.getPlantes() != null) {
                            for (Plante plante : parcelle.getPlantes()) {
                                // Fertilizer effect: grow faster (gain extra age)
                                plante.setAge(plante.getAge() + 1);
                                planteRepository.save(plante);
                            }
                        }
                    }
                }
            }
        }

        // 3.5. Insect proliferation (reproduction)
        for (Parcelle parcelle : parcelles) {
            List<Insecte> insects = parcelle.getInsectes();
            if (insects == null) continue;
           long males = insects.stream().filter(i -> "Male".equalsIgnoreCase(i.getSexe())).count();
           long females = insects.stream().filter(i -> "Femelle".equalsIgnoreCase(i.getSexe())).count();
           if (males > 0 && females > 0) {
                if (Math.random() < 0.3) { // 30% chance per step
                    Insecte newInsect = new Insecte();
                    newInsect.setEspece(insects.get(0).getEspece());
                    newInsect.setSexe(Math.random() < 0.5 ? "Male" : "Femelle");
                    newInsect.setSante(10);
                    newInsect.setMobilite(insects.get(0).getMobilite());
                    newInsect.setResistanceInsecticide(insects.get(0).getResistanceInsecticide());
                    newInsect.setParcelle(parcelle);
                    insecteRepository.save(newInsect);
               }
            }
        }

        // Increment simulation step
        currentStep++;
    }
} 