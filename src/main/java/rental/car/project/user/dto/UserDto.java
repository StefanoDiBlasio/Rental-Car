package rental.car.project.user.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import rental.car.project.user.domain.RoleType;
import rental.car.project.common.base.BaseGetDto;

import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
public class UserDto extends BaseGetDto {

    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private RoleType roleType;
}
