package rental.car.project.prenotazione.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import rental.car.project.prenotazione.domain.Prenotazione;
import rental.car.project.prenotazione.dto.PrenotazioneDto;

import java.util.List;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long>, JpaSpecificationExecutor<Prenotazione> {

    List<Prenotazione> findAllPrenotazioniByUserId(Long userId);
}
