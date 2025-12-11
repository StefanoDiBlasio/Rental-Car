package rental.car.project.prenotazione.application;

import java.time.LocalDate;

public record PrenotazioneUpdatedEvent(Long prenotazioneId, LocalDate inizioPrenotazione, LocalDate finePrenotazione) {
}
