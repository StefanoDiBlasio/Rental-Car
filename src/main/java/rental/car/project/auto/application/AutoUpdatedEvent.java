package rental.car.project.auto.application;

import rental.car.project.auto.domain.AutoType;

public record AutoUpdatedEvent(Long autoId, String casaCostruttrice, String modello, Integer annoImmatricolazione, String targa, AutoType autoType) {
}
