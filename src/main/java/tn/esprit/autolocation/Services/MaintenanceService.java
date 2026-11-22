package tn.esprit.autolocation.Services;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autolocation.Entities.Maintenance;
import tn.esprit.autolocation.Repositories.MaintenanceRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class MaintenanceService implements IMaintenanceService {
    private MaintenanceRepository maintenanceRepository;

    @Override
    public Maintenance ajouterMaintenance(Maintenance maintenance) {
        return maintenanceRepository.save(maintenance);
    }

    @Override
    public void supprimerMaintenance(Long id) {
        maintenanceRepository.deleteById(id);
    }

    @Override
    public List<Maintenance> afficherMaintenances() {
        return maintenanceRepository.findAll();
    }

    @Override
    public Maintenance modifierMaintenance(Maintenance maintenance) {
        return maintenanceRepository.save(maintenance);
    }

    @Override
    public Maintenance afficherMaintenanceByID(Long id) {
        return maintenanceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Maintenance not found: " + id));
    }
}
