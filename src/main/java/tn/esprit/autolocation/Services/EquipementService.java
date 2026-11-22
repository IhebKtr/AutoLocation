package tn.esprit.autolocation.Services;

import lombok.AllArgsConstructor;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autolocation.Entities.Equipement;
import tn.esprit.autolocation.Entities.Vehicule;
import tn.esprit.autolocation.Repositories.EquipementRepository;
import tn.esprit.autolocation.Repositories.VehiculeRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class EquipementService implements IEquipementService{
    EquipementRepository equipementRepository;
    VehiculeRepository vehiculeRepository;
    @Override
    public Equipement ajouterEquipement(Equipement equipement) {
        return equipementRepository.save(equipement);
    }

    @Override
    public void supprimerEquipement(Long id) {
        equipementRepository.deleteById(id);
    }

    @Override
    public List<Equipement> afficherEquipements() {
        return equipementRepository.findAll();
    }

    @Override
    public Equipement modifierEquipement(Equipement equipement) {
        return equipementRepository.save(equipement);
    }

    @Override
    public Equipement AfficherEquipementByID(Long id) {
        return equipementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Equipement not found: " + id));
    }

    @Override
    @Transactional
    public void affecterEquipementAVehicule(Long idEquipement, Long idVehicule) {
        Equipement equipement = equipementRepository.findById(idEquipement)
                .orElseThrow(() -> new EntityNotFoundException("Equipement not found: " + idEquipement));
        Vehicule vehicule = vehiculeRepository.findById(idVehicule)
                .orElseThrow(() -> new EntityNotFoundException("Vehicule not found: " + idVehicule));
        if (!vehicule.getEquipements().contains(equipement)) {
            vehicule.getEquipements().add(equipement);
        }
        if (!equipement.getVehicules().contains(vehicule)) {
            equipement.getVehicules().add(vehicule);
        }
        vehiculeRepository.save(vehicule);
        equipementRepository.save(equipement);

    }

    @Override
    @Transactional
    public void desaffecterEquipementDeVehicule(Long idEquipement, Long idVehicule) {
        Equipement equipement = equipementRepository.findById(idEquipement)
                .orElseThrow(() -> new EntityNotFoundException("Equipement not found: " + idEquipement));
        Vehicule vehicule = vehiculeRepository.findById(idVehicule)
                .orElseThrow(() -> new EntityNotFoundException("Vehicule not found: " + idVehicule));
        vehicule.getEquipements().remove(equipement);
        equipement.getVehicules().remove(vehicule);
        vehiculeRepository.save(vehicule);
        equipementRepository.save(equipement);

    }
}
