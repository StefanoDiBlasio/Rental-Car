package rental.car.project.prenotazione.application;

import rental.car.project.auto.domain.AutoType;
import rental.car.project.prenotazione.domain.StatusPrenotazione;

import java.time.LocalDate;

public record PrenotazioneCreatedEvent(Long prenotazioneId, Long userId, String username, String firstName, String lastName,
                                       Long autoId, String casaCostruttrice, String modello, Integer annoImmatricolazione,
                                       String targa, String autoType, LocalDate inizioPrenotazione,
                                       LocalDate finePrenotazione, StatusPrenotazione status) {
}
