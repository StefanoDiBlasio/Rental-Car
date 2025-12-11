package rental.car.project.prenotazione.application;

import java.time.LocalDate;

public record PrenotazioneCreatedEvent(Long prenotazioneId, Long userId, Long autoId, LocalDate inizioPrenotazione, LocalDate finePrenotazione) {
}
