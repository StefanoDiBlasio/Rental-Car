package rental.car.project.prenotazione.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import rental.car.project.auto.domain.Auto;
import rental.car.project.prenotazione.domain.Prenotazione;
import rental.car.project.prenotazione.domain.StatusPrenotazione;
import rental.car.project.user.domain.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PrenotazioneSpecification {

    public static Specification<Prenotazione> withFilters(
            Long id,
            String firstName,
            String lastName,
            String casaCostruttrice,
            String modello,
            String targa,
            LocalDate inizioPrenotazione,
            LocalDate finePrenotazione,
            StatusPrenotazione status
    ) {
        return (((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (id != null) {
                predicates.add(criteriaBuilder.equal(root.get("id"), id));
            }

            if (inizioPrenotazione != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                        root.get("inizioPrenotazione"), inizioPrenotazione));
            }

            if (finePrenotazione != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                        root.get("finePrenotazione"), finePrenotazione));
            }

            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }

// Join USER
            Join<Prenotazione, User> userJoin = root.join("user", JoinType.LEFT);

            if (firstName != null && !firstName.isBlank()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(userJoin.get("firstName")),
                        "%" + firstName.toLowerCase() + "%"
                ));
            }

            if (lastName != null && !lastName.isBlank()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(userJoin.get("lastName")),
                        "%" + lastName.toLowerCase() + "%"
                ));
            }

// Join AUTO
            Join<Prenotazione, Auto> autoJoin = root.join("auto", JoinType.LEFT);

            if (casaCostruttrice != null && !casaCostruttrice.isBlank()) {
                predicates.add(criteriaBuilder.equal(
                        autoJoin.get("casaCostruttrice"),
                        casaCostruttrice
                ));
            }

            if (modello != null && !modello.isBlank()) {
                predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(autoJoin.get("modello")),
                        "%" + modello.toLowerCase() + "%"
                ));
            }

            if (targa != null && !targa.isBlank()) {
                predicates.add(criteriaBuilder.equal(
                        autoJoin.get("targa"),
                        targa
                ));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }));
    }
}