package rental.car.project.prenotazione.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PrenotazioneCreateDto {

    private Long userId;
    private Long autoId;
    private LocalDate inizioPrenotazione;
    private LocalDate finePrenotazione;
}
