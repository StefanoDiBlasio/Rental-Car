package rental.car.project.auto.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rental.car.project.auto.domain.Auto;

@Repository
public interface AutoRepository extends JpaRepository<Auto, Long> {
}
