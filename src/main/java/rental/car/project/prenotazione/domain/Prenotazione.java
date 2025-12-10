package rental.car.project.prenotazione.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rental.car.project.auto.domain.Auto;
import rental.car.project.user.domain.User;
import rental.car.project.utils.BaseEntity;

import java.time.LocalDate;

@Entity
@Table(name = "prenotazioni")
@NoArgsConstructor
@Getter
@Setter
public class Prenotazione extends BaseEntity {

    @Column(name = "user_id", nullable = false, insertable = false, updatable = false)
    private Long userId;

    @Column(name = "auto_id", nullable = false, insertable = false, updatable = false)
    private Long autoId;

    @Column(name = "inizio_prenotazione", nullable = false)
    private LocalDate inizioPrenotazione;

    @Column(name = "fine_prenotazione", nullable = false)
    private LocalDate finePrenotazione;

    //---------------------------------------------

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auto_id", nullable = false, insertable = false, updatable = false)
    private Auto auto;
}
