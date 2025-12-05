package rental.car.project.user.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationRequestDto {

    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
}
