package tn.esprit.autolocation.Services;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autolocation.Entities.Employe;
import tn.esprit.autolocation.Repositories.EmployeRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeService implements IEmployeService {
    private EmployeRepository employeRepository;

    @Override
    public Employe ajouterEmploye(Employe employe) {
        return employeRepository.save(employe);
    }

    @Override
    public void supprimerEmploye(Long id) {
        employeRepository.deleteById(id);
    }

    @Override
    public List<Employe> afficherEmployes() {
        return employeRepository.findAll();
    }

    @Override
    public Employe modifierEmploye(Employe employe) {
        return employeRepository.save(employe);
    }

    @Override
    public Employe afficherEmployeByID(Long id) {
        return employeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employe not found: " + id));
    }
}
