package rental.car.project.prenotazione.dto;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import rental.car.project.auto.domain.AutoType;
import rental.car.project.common.base.BaseGetDto;
import rental.car.project.prenotazione.domain.StatusPrenotazione;

import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
public class PrenotazioneDto extends BaseGetDto {

    private Long userId;
    private String username;
    private String firstName;
    private String lastName;
    private Long autoId;
    private String casaCostruttrice;
    private String modello;
    private Integer annoImmatricolazione;
    private String targa;
    private AutoType autoType;
    private LocalDate inizioPrenotazione;
    private LocalDate finePrenotazione;
    private StatusPrenotazione status;
}
