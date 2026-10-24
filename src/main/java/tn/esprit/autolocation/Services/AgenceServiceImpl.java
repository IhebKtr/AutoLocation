package tn.esprit.autolocation.Services;

import lombok.AllArgsConstructor;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autolocation.Entities.Agence;
import tn.esprit.autolocation.Entities.Vehicule;
import tn.esprit.autolocation.Repositories.AgenceRepository;
import tn.esprit.autolocation.Repositories.VehiculeRepository;
import java.util.List;
@Service
@AllArgsConstructor

public class AgenceServiceImpl implements IAgenceSerive{

    private VehiculeRepository vehiculeRepository;
    private AgenceRepository agenceRepository ;
    @Override
    public Agence ajouterAgence(Agence agence) {
        return agenceRepository.save(agence);
    }

    @Override
    public void supprimerAgence(Long id) {
agenceRepository.deleteById(id);
    }

    @Override
    public List<Agence> afficherAgences() {
        return agenceRepository.findAll();
    }

    @Override
    public Agence modifierAgence(Agence agence) {
        return agenceRepository.save(agence);
    }

    @Override
    public Agence AfficherAgenceByID(Long id) {
       return  agenceRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void affecterVehiculeAAgence(Long idVehicule, Long idAgence) {
        Vehicule vehicule = vehiculeRepository.findById(idVehicule)
                .orElseThrow(() -> new EntityNotFoundException("Vehicule not found: " + idVehicule));
        Agence agence = agenceRepository.findById(idAgence)
                .orElseThrow(() -> new EntityNotFoundException("Agence not found: " + idAgence));

        Agence ancienneAgence = vehicule.getAgence();
        if (ancienneAgence != null && !ancienneAgence.getId().equals(idAgence)) {
            ancienneAgence.getVehicules().remove(vehicule);
            agenceRepository.save(ancienneAgence);
        }

        vehicule.setAgence(agence);
        if (!agence.getVehicules().contains(vehicule)) {
            agence.getVehicules().add(vehicule);
        }
        vehiculeRepository.save(vehicule);
    }

    @Transactional
    @Override
    public void desaffecterVehiculeDAgence(Long idVehicule) {
        Vehicule vehicule = vehiculeRepository.findById(idVehicule)
                .orElseThrow(() -> new EntityNotFoundException("Vehicule not found: " + idVehicule));

        Agence agence = vehicule.getAgence();
        if (agence != null) {
            agence.getVehicules().remove(vehicule);
            vehicule.setAgence(null);
            agenceRepository.save(agence);
            vehiculeRepository.save(vehicule);
        }
    }
}
