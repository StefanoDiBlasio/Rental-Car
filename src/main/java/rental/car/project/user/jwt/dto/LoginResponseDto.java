package rental.car.project.user.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto implements Serializable {

    private Long userId;
    private String username;
    private String token;
    private String role;

}
