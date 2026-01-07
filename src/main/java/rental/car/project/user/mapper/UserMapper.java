package rental.car.project.user.mapper;

import org.springframework.stereotype.Component;
import rental.car.project.user.domain.User;
import rental.car.project.user.dto.UserDto;
import rental.car.project.user.dto.UserUpdateDto;
import rental.car.project.common.base.BaseMapper;

@Component
public class UserMapper implements BaseMapper<User, UserDto> {

    @Override
    public UserDto convertToDto(User entity) {
        UserDto dto = UserDto.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .birthDate(entity.getBirthDate())
                .roleType(entity.getRoleType())
                .enabled(entity.getEnabled())
                .build();

        return dto;
    }

    @Override
    public User convertToEntity(UserDto dto) {
        User entity = User.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .birthDate(dto.getBirthDate())
                .roleType(dto.getRoleType())
                .enabled(dto.getEnabled())
                .build();

        return entity;
    }

    public User convertToUpdateEntity(User entity, UserUpdateDto updateDto) {
        if(updateDto.getFirstName() != null && !updateDto.getFirstName().trim().isEmpty()) {
            entity.setFirstName(updateDto.getFirstName());
        }

        if(updateDto.getLastName() != null && !updateDto.getLastName().trim().isEmpty()) {
            entity.setLastName(updateDto.getLastName());
        }

        if(updateDto.getBirthDate() != null) {
            entity.setBirthDate(updateDto.getBirthDate());
        }

        return entity;
    }

}
