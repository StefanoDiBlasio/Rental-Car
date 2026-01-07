package rental.car.project.auto.specification;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import rental.car.project.auto.domain.Auto;
import rental.car.project.auto.domain.AutoType;

import java.util.ArrayList;
import java.util.List;

public class AutoSpecification {

    public static Specification<Auto> withFilters(
            String casaCostruttrice,
            String modello,
            Integer annoImmatricolazione,
            AutoType autoType
    ){
        return((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(casaCostruttrice != null){
                predicates.add(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("casaCostruttrice")),
                                "%" + casaCostruttrice.toLowerCase() + "%")
                );
            }

            if (modello != null) {
                predicates.add(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("modello")),
                                "%" + modello.toLowerCase() + "%")
                );
            }

            if (annoImmatricolazione != null) {
                predicates.add(
                        criteriaBuilder.equal(root.get("annoImmatricolazione"),
                                annoImmatricolazione)
                );
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }

}
