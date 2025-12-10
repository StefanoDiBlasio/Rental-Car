package rental.car.project.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserUpdateDto {

    private String firstName;
    private String lastName;
    private LocalDate birthDate;
}
