package rental.car.project.auto.dto;

import lombok.Getter;
import lombok.Setter;
import rental.car.project.auto.domain.AutoType;

@Getter
@Setter
public class AutoCreateDto {

    private String casaCostruttrice;
    private String modello;
    private Integer annoImmatricolazione;
    private String targa;
    private AutoType autoType;
}
