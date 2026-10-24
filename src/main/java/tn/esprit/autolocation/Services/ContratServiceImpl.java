package tn.esprit.autolocation.Services;

import lombok.AllArgsConstructor;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autolocation.Entities.Contrat;
import tn.esprit.autolocation.Entities.Reservation;
import tn.esprit.autolocation.Repositories.ContratRepository;
import tn.esprit.autolocation.Repositories.ReservationRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ContratServiceImpl implements IContratService{
    private ContratRepository contratRepository ;
    private ReservationRepository reservationRepository;

    @Override
    public Contrat ajouterContrat(Contrat contart) {
        return contratRepository.save(contart);
    }

    @Override
    public void supprimerContrat(Long id) {
contratRepository.deleteById(id);
    }

    @Override
    public List<Contrat> afficherContrats() {
        return contratRepository.findAll();
    }

    @Override
    public Contrat modifierContrat(Contrat contrat) {
        return contratRepository.save(contrat);
    }

    @Override
    public Contrat AfficherContratByID(Long id) {
        return contratRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void affecterContratAReservation(Long idContrat, Long idReservation) {
        Contrat contrat = contratRepository.findById(idContrat)
                .orElseThrow(() -> new EntityNotFoundException("Contrat not found: " + idContrat));
        Reservation reservation = reservationRepository.findById(idReservation)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found: " + idReservation));

        if ((contrat.getReservation() != null && !contrat.getReservation().getId().equals(idReservation))
                || (reservation.getContrat() != null
                && !reservation.getContrat().getId().equals(idContrat))) {
            throw new IllegalStateException("Contrat or reservation is already associated");
        }

        contrat.setReservation(reservation);
        reservation.setContrat(contrat);
        contratRepository.save(contrat);
        reservationRepository.save(reservation);
    }

    @Override
    @Transactional
    public void desaffecterContratDeReservation(Long idReservation) {
        Reservation reservation = reservationRepository.findById(idReservation)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found: " + idReservation));
        Contrat contrat = reservation.getContrat();
        if (contrat != null) {
            contrat.setReservation(null);
            reservation.setContrat(null);
            contratRepository.save(contrat);
            reservationRepository.save(reservation);
        }
    }
    }
