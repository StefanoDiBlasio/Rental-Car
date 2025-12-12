package rental.car.project.user.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import rental.car.project.user.domain.User;
import rental.car.project.user.jwt.dto.RegistrationRequestDto;
import rental.car.project.common.base.BaseMapper;

@Component
public class RegistrationMapper implements BaseMapper<User, RegistrationRequestDto> {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public RegistrationRequestDto convertToDto(User entity) {
        return null;
    }

    @Override
    public User convertToEntity(RegistrationRequestDto dto) {
        User entity = User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .birthDate(dto.getBirthDate())
                .build();

        return entity;
    }
}
