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
        List<Plante> plantes = planteRepository.findAll();
        List<Parcelle> parcelles = parcelleRepository.findAll();
        List<Insecte> insectes = insecteRepository.findAll();
        List<Dispositif> dispositifs = dispositifRepository.findAll();

        ageAndFruitPlants(plantes);
        drageonnantesColonization(parcelles);
        insectBehavior(insectes, parcelles);
        applyDevices(dispositifs, parcelles);
        insectProliferation(parcelles);
        insectColonization(parcelles);

        currentStep++;
    }

    /**
     * vérifie la maturité et augmente le nombre de fruits pour les plantes matures.
     */
    private void ageAndFruitPlants(List<Plante> plantes) {
        for (Plante plante : plantes) {
            plante.setAge(plante.getAge() + 1);
            if (plante.getAge() >= plante.getAgeMaturite()) {
                plante.setMature(true);
            }
            if (plante.isMature()) {
                plante.setFruits(plante.getFruits() + 1);
            }
            planteRepository.save(plante);
        }
    }

    /**
     * Gère la colonisation des plantes drageonnantes vers les parcelles voisines.
     */
    private void drageonnantesColonization(List<Parcelle> parcelles) {
        for (Parcelle parcelle : parcelles) {
            if (parcelle.getPlantes() == null) continue;
            for (Plante plante : parcelle.getPlantes()) {
                if (plante.isMature() && plante.isDrageonnante()) {
                    int[][] directions = {{0,1},{1,0},{0,-1},{-1,0}};
                    for (int[] dir : directions) {
                        int nx = parcelle.getX() + dir[0];
                        int ny = parcelle.getY() + dir[1];
                        Parcelle neighbor = parcelles.stream()
                            .filter(p -> p.getX() == nx && p.getY() == ny)
                            .findFirst()
                            .orElse(null);
                        if (neighbor != null && (neighbor.getPlantes() == null || neighbor.getPlantes().isEmpty())) {
                            if (Math.random() < plante.getTauxColonisation()) {
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
    }

    /**
     * Gère l'alimentation, la santé, la mort et le déplacement des insectes.
     */
    private void insectBehavior(List<Insecte> insectes, List<Parcelle> parcelles) {
        for (Insecte insecte : insectes) {
            Parcelle currentParcelle = insecte.getParcelle();
            boolean fed = false;
            if (currentParcelle != null && currentParcelle.getPlantes() != null && !currentParcelle.getPlantes().isEmpty()) {
                fed = true;
                insecte.setSante(Math.min(10, insecte.getSante() + 1));
                insecte.setStepsWithoutFeeding(0);
            } else {
                insecte.setSante(insecte.getSante() - 1);
                insecte.setStepsWithoutFeeding(insecte.getStepsWithoutFeeding() + 1);
            }
            if (insecte.getSante() <= 0 || insecte.getStepsWithoutFeeding() >= 5) {
                insecteRepository.delete(insecte);
                continue;
            }
            // Déplacement aléatoire :
            if (Math.random() < insecte.getMobilite()) {
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
    }

    /**
     * Applique les effets des dispositifs (arrosage, insecticide, engrais) aux parcelles concernées si actifs.
     */
    private void applyDevices(List<Dispositif> dispositifs, List<Parcelle> parcelles) {
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
                                if (Math.random() > insecte.getResistanceInsecticide()) {
                                    insecteRepository.delete(insecte);
                                } else {
                                    insecte.setSante(Math.max(0, insecte.getSante() - 5));
                                    insecteRepository.save(insecte);
                                }
                            }
                        }
                    }
                    if ("Engrais".equalsIgnoreCase(activeType)) {
                        if (parcelle.getPlantes() != null) {
                            for (Plante plante : parcelle.getPlantes()) {
                                plante.setAge(plante.getAge() + 1);
                                planteRepository.save(plante);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Gère la reproduction (prolifération) des insectes sur la même parcelle.
     */
    private void insectProliferation(List<Parcelle> parcelles) {
        for (Parcelle parcelle : parcelles) {
            List<Insecte> insects = parcelle.getInsectes();
            if (insects == null) continue;
            long males = insects.stream().filter(i -> "Male".equalsIgnoreCase(i.getSexe())).count();
            long females = insects.stream().filter(i -> "Femelle".equalsIgnoreCase(i.getSexe())).count();
            if (males > 0 && females > 0) {
                if (Math.random() < 0.3) {
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
    }

    /**
     * Gère la colonisation des insectes vers de nouvelles parcelles voisines.
     */
    private void insectColonization(List<Parcelle> parcelles) {
        for (Parcelle parcelle : parcelles) {
            List<Insecte> insects = parcelle.getInsectes();
            if (insects == null || insects.isEmpty()) continue;
            for (Insecte parent : insects) {
                int[][] directions = {{0,1},{1,0},{0,-1},{-1,0}};
                for (int[] dir : directions) {
                    int nx = parcelle.getX() + dir[0];
                    int ny = parcelle.getY() + dir[1];
                    Parcelle neighbor = parcelles.stream()
                        .filter(p -> p.getX() == nx && p.getY() == ny)
                        .findFirst()
                        .orElse(null);
                    if (neighbor != null) {
                        if ((neighbor.getInsectes() == null || neighbor.getInsectes().isEmpty()) && Math.random() < 0.2) {
                            Insecte newInsect = new Insecte();
                            newInsect.setEspece(parent.getEspece());
                            newInsect.setSexe(Math.random() < 0.5 ? "Male" : "Femelle");
                            newInsect.setSante(10);
                            newInsect.setMobilite(parent.getMobilite());
                            newInsect.setResistanceInsecticide(parent.getResistanceInsecticide());
                            newInsect.setParcelle(neighbor);
                            insecteRepository.save(newInsect);
                        }
                    }
                }
            }
        }
    }
} 