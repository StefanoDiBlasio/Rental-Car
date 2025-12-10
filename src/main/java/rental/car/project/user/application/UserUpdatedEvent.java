package rental.car.project.user.application;

import java.time.LocalDate;

public record UserUpdatedEvent(Long userId, String firstName, String lastName, LocalDate birthDate) {
}
