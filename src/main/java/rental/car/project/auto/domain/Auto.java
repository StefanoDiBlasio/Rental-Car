package rental.car.project.auto.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import rental.car.project.common.base.BaseEntity;

@Entity
@Table(name = "auto")
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Auto extends BaseEntity {

    @Column(name = "casa_costruttrice", nullable = false)
    private String casaCostruttrice;
    @Column(name = "modello", nullable = false)
    private String modello;
    @Column(name = "anno_immatricolazione", nullable = false)
    private Integer annoImmatricolazione;
    @Column(name = "targa", nullable = false)
    private String targa;
    @Column(name = "tipo_auto", nullable = false)
    @Enumerated(EnumType.STRING)
    private AutoType autoType;

    public Auto(String casaCostruttrice, String modello, Integer annoImmatricolazione, String targa, AutoType autoType) {
        this.casaCostruttrice = casaCostruttrice;
        this.modello = modello;
        this.annoImmatricolazione = annoImmatricolazione;
        this.targa = targa;
        this.autoType = autoType;
    }
}
