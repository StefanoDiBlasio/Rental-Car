package rental.car.project.prenotazione.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import rental.car.project.auto.domain.Auto;
import rental.car.project.user.domain.User;
import rental.car.project.utils.BaseGetDto;

import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
public class PrenotazioneDto extends BaseGetDto {

    private User user;
    private Auto auto;
    private LocalDate inizioPrenotazione;
    private LocalDate finePrenotazione;
}
