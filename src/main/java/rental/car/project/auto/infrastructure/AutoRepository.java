package rental.car.project.auto.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import rental.car.project.auto.domain.Auto;

import java.util.Optional;

@Repository
public interface AutoRepository extends JpaRepository<Auto, Long>, JpaSpecificationExecutor<Auto> {

    Optional<Auto> findByTarga(String targa);
}
