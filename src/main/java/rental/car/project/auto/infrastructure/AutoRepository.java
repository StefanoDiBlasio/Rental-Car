package rental.car.demo.auto.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rental.car.demo.auto.domain.Auto;

@Repository
public interface AutoRepository extends JpaRepository<Auto, Long> {
}
