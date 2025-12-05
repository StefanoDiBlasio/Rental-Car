package rental.car.project.user.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String nome;
    private String cognome;
    private String dataDiNascita;
    private RoleType roleType;
}
