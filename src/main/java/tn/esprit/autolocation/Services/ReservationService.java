package tn.esprit.autolocation.Services;

import lombok.AllArgsConstructor;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autolocation.Entities.Client;
import tn.esprit.autolocation.Entities.Reservation;
import tn.esprit.autolocation.Repositories.ClientRepository;
import tn.esprit.autolocation.Repositories.ReservationRepository;

import java.util.List;
@Service
@AllArgsConstructor
public class ReservationService implements IReservationService {
ReservationRepository reservationRepository ;
    private ClientRepository clientRepository;
    @Override
    public Reservation ajouterReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public void supprimerReservation(Long id) {
      reservationRepository.deleteById(id);
    }

    @Override
    public List<Reservation> afficherReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation modifierReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation AfficherReservationByID(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void affecterClientAReservation(Long idClient, Long idReservation) {
        Client client = clientRepository.findById(idClient)
                .orElseThrow(() -> new EntityNotFoundException("Client not found: " + idClient));
        Reservation reservation = reservationRepository.findById(idReservation)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found: " + idReservation));

        Client ancienClient = reservation.getClient();
        if (ancienClient != null && !ancienClient.getId().equals(idClient)) {
            ancienClient.getReservations().remove(reservation);
        }
        reservation.setClient(client);
        if (!client.getReservations().contains(reservation)) {
            client.getReservations().add(reservation);
        }
        reservationRepository.save(reservation);
    }
}
