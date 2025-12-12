package rental.car.project.prenotazione.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import rental.car.project.common.base.BaseGetDto;

import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
public class PrenotazioneDto extends BaseGetDto {

    private Long userId;
    private Long autoId;
    private LocalDate inizioPrenotazione;
    private LocalDate finePrenotazione;
}
