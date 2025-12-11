package rental.car.project.prenotazione.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PrenotazioneUpdateDto {

    private LocalDate inizioPrenotazione;
    private LocalDate finePrenotazione;
}
