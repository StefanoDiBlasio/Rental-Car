package rental.car.project.prenotazione.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import rental.car.project.auto.domain.Auto;
import rental.car.project.user.domain.User;
import rental.car.project.common.base.BaseEntity;

import java.time.LocalDate;

@Entity
@Table(name = "prenotazioni")
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Prenotazione extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auto_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Auto auto;

    @Column(name = "inizio_prenotazione", nullable = false)
    private LocalDate inizioPrenotazione;

    @Column(name = "fine_prenotazione", nullable = false)
    private LocalDate finePrenotazione;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusPrenotazione status;

}
