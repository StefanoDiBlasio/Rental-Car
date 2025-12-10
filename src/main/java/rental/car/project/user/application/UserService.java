package rental.car.project.user.application;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import rental.car.project.user.domain.RoleType;
import rental.car.project.user.domain.User;
import rental.car.project.user.dto.UserDto;
import rental.car.project.user.dto.UserUpdateDto;
import rental.car.project.user.infrastructure.UserRepository;
import rental.car.project.user.jwt.dto.RegistrationRequestDto;
import rental.car.project.user.mapper.RegistrationMapper;
import rental.car.project.user.mapper.UserMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private RegistrationMapper registrationMapper;

    public UserDto getUserById(Long userId) {
        logger.info("::UserService.get (START)::");
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("::User non trovato!::"));
        logger.info("::GET_BY_ID Utente trovato con id: " + userId + ": " + user.getFirstName() + " " + user.getLastName() + " ::");
        return userMapper.convertToDto(user);
    }

    public List<UserDto> getAllUsers() {
        logger.info("::UserService.getAll (START)::");
        List<UserDto> dtoList = new ArrayList<>();
        List<User> users = userRepository.findAll();
        for (User u : users) {
            dtoList.add(userMapper.convertToDto(u));
        }
        return dtoList;
    }

    public UserDto createUser(RegistrationRequestDto dto, RoleType roleType) {
        logger.info("::AuthenticationService.createUser:: (START)");

        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("::Username già presente!::");
        }

        User user = registrationMapper.convertToEntity(dto);
        user.setRoleType(roleType);
        user.setEnabled(true);
        user = userRepository.save(user);
        logger.info(":: User creato con successo! ::");
        publisher.publishEvent(new UserCreatedEvent(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthDate(),
                user.getEnabled(),
                user.getRoleType()
        ));
        logger.info("::AuthenticationService.register:: (END)");

        return userMapper.convertToDto(user);
    }

    public UserDto updateUser(Long userId, UserUpdateDto updateDto) {
        logger.info("::UserService.update (START)::");
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("::Nessun utente trovato!::"));

        userMapper.converToUpdateEntity(existingUser, updateDto);
        userRepository.save(existingUser);
        publisher.publishEvent(new UserUpdatedEvent(
                existingUser.getId(),
                existingUser.getFirstName(),
                existingUser.getLastName(),
                existingUser.getBirthDate()
        ));
        logger.info("::UPDATE Utente con id: " + userId + " aggiornato con successo!::");
        return userMapper.convertToDto(existingUser);
    }

    public void deleteUser(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        }
        publisher.publishEvent(new UserDeletedEvent(
                userId
        ));
        logger.info("::Utente con id: " + userId + " eliminato con successo!::");
    }

    public void setEnabled(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("::Nessun utente trovato!::"));

        user.setEnabled(true);
        userRepository.save(user);
        logger.info("::Lo stato dell'utente con id: " + userId + " e' ora abilitato!::");
    }

    public void setDisabled(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("::Nessun utente trovato!::"));

        user.setEnabled(false);
        userRepository.save(user);
        logger.info("::Lo stato dell'utente con id: " + userId + " e' ora disabilitato!::");
    }
}
