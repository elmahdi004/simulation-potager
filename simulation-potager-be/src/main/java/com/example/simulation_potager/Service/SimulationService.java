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

    public void step() {
        // 1. Age plants and handle maturity
        List<Plante> plantes = planteRepository.findAll();
        for (Plante plante : plantes) {
            plante.setAge(plante.getAge() + 1);
            if (plante.getAge() >= plante.getAgeMaturite()) {
                plante.setMature(true);
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

        // 4. Treatment devices: apply watering (Arrosage)
        List<Dispositif> dispositifs = dispositifRepository.findAll();
        for (Dispositif dispositif : dispositifs) {
            // For now, always active (expand with schedule logic if needed)
            Parcelle center = dispositif.getParcelle();
            int rayon = dispositif.getRayon();
            String type = dispositif.getClass().getSimpleName(); // Or use a type field if available

            for (Parcelle parcelle : parcelles) {
                int dx = Math.abs(parcelle.getX() - center.getX());
                int dy = Math.abs(parcelle.getY() - center.getY());
                if (dx + dy <= rayon) { // Manhattan distance
                    // For now, only handle watering
                    parcelle.setHumidite(Math.min(1.0, parcelle.getHumidite() + 0.3)); // Increase humidity
                    parcelleRepository.save(parcelle);
                }
            }
        }

        // TODO: Add logic for insects and treatments
    }
} 