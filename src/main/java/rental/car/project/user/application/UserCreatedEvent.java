package rental.car.project.user.application;

import rental.car.project.user.domain.RoleType;

import java.time.LocalDate;

public record UserCreatedEvent(Long userId, String username, String password, String firstName, String lastName, LocalDate birthDate, Boolean enabled, RoleType roleType) {
}
