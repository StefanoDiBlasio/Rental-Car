package rental.car.project.prenotazione.application;

import rental.car.project.prenotazione.domain.StatusPrenotazione;

import java.time.LocalDate;

public record PrenotazioneUpdatedEvent(Long prenotazioneId, LocalDate inizioPrenotazione, LocalDate finePrenotazione, StatusPrenotazione status) {
}
