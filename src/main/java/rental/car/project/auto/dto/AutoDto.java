package rental.car.project.auto.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import rental.car.project.auto.domain.AutoType;
import rental.car.project.utils.base.BaseGetDto;

@Getter
@Setter
@SuperBuilder
public class AutoDto extends BaseGetDto {

    private String casaCostruttrice;
    private String modello;
    private Integer annoImmatricolazione;
    private String targa;
    private AutoType autoType;
}
