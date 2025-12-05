package rental.car.demo.auto.application;

import rental.car.demo.auto.domain.AutoType;

public record AutoCreatedEvent(Long autoId, String casaCostruttrice, String modello, Integer annoImmatricolazione, String targa, AutoType autoType) {
}
