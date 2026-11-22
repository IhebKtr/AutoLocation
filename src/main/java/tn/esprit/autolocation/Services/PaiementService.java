package tn.esprit.autolocation.Services;

import lombok.AllArgsConstructor;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autolocation.Entities.Contrat;
import tn.esprit.autolocation.Entities.Paiement;
import tn.esprit.autolocation.Repositories.ContratRepository;
import tn.esprit.autolocation.Repositories.PaiementRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class PaiementService implements IPaiementService {
    PaiementRepository paiementRepository ;
    ContratRepository contratRepository;
    @Override
    public Paiement ajouterPaiement(Paiement paiement) {
        return paiementRepository.save(paiement);
    }

    @Override
    public void supprimerPaiement(Long id) {
        paiementRepository.deleteById(id);

    }

    @Override
    public List<Paiement> afficherPaiements() {
        return paiementRepository.findAll();
    }

    @Override
    public Paiement modifierPaiement(Paiement paiement) {
        return paiementRepository.save(paiement);
    }

    @Override
    public Paiement AfficherPaiementByID(Long id) {
        return paiementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Paiement not found: " + id));
    }

    @Override
    @Transactional
    public void affecterPaiementAContrat(Long idPaiement, Long idContrat) {
        Paiement paiement = paiementRepository.findById(idPaiement)
                .orElseThrow(() -> new EntityNotFoundException("Paiement not found: " + idPaiement));
        Contrat contrat = contratRepository.findById(idContrat)
                .orElseThrow(() -> new EntityNotFoundException("Contrat not found: " + idContrat));

        Contrat ancienContrat = paiement.getContrat();
        if (ancienContrat != null && !ancienContrat.getId().equals(idContrat)) {
            throw new IllegalStateException("Paiement is already associated with another contrat");
        }
        paiement.setContrat(contrat);
        if (!contrat.getPaiements().contains(paiement)) {
            contrat.getPaiements().add(paiement);
        }
        paiementRepository.save(paiement);
    }
}