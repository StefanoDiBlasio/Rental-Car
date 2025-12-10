package rental.car.project.auto.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import rental.car.project.auto.domain.Auto;
import rental.car.project.auto.domain.AutoType;
import rental.car.project.auto.infrastructure.AutoRepository;

@Service
public class AutoService {

    @Autowired
    private AutoRepository autoRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    public AutoService(AutoRepository autoRepository, ApplicationEventPublisher publisher) {
        this.autoRepository = autoRepository;
        this.publisher = publisher;
    }

    public Auto createAuto(String casaCostruttrice, String modello, Integer annoImmatricolazione, String targa, AutoType autoType) {
        Auto auto = autoRepository.save(new Auto(casaCostruttrice, modello, annoImmatricolazione, targa, autoType));
        publisher.publishEvent(new AutoCreatedEvent(auto.getId(), casaCostruttrice, modello, annoImmatricolazione, targa, autoType));
        return auto;
    }
}
