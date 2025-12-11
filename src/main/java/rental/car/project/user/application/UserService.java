package rental.car.project.user.application;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.DisabledException;
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


    public List<UserDto> getAllUsers(){
        logger.info("::UserService.getAllUsers (START)::");
        List<UserDto> dtoList = new ArrayList<>();
        List<User> users = userRepository.findAll();
        for (User u : users) {
            dtoList.add(userMapper.convertToDto(u));
        }
        return dtoList;
    }

    public UserDto getUserById(Long userId) {
        logger.info("::UserService.getUserById (START)::");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("::User con Id: " + userId + " non trovato!::"));
        logger.info("::GET_USER_BY_ID Utente trovato con id: " + userId + ": " + user.getFirstName() + " " + user.getLastName() + " ::");
        return userMapper.convertToDto(user);
    }

    public UserDto createUser(RegistrationRequestDto dto, RoleType roleType) {
        logger.info("::UserService.createUser:: (START)");
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
        logger.info("::UserService.register:: (END)");

        return userMapper.convertToDto(user);
    }

    public UserDto updateUser(Long userId, UserUpdateDto updateDto) {
        logger.info("::UserService.updateUser (START)::");
        if (noFieldsToUpdate(updateDto)) {
            logger.error("::ERRORE:: Nessun campo da aggiornare!");
            throw new IllegalArgumentException("Non ci sono campi da aggiornare!");
        }
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("::Nessun utente trovato!::"));

        existingUser = userMapper.convertToUpdateEntity(existingUser, updateDto);
        existingUser = userRepository.save(existingUser);
        publisher.publishEvent(new UserUpdatedEvent(
                existingUser.getId(),
                existingUser.getFirstName(),
                existingUser.getLastName(),
                existingUser.getBirthDate()
        ));
        logger.info("::UPDATE_USER Utente con id: " + userId + " aggiornato con successo!::");
        return userMapper.convertToDto(existingUser);
    }

    public void deleteUser(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        }
        publisher.publishEvent(new UserDeletedEvent(
                userId
        ));
        logger.info("::DELETE_USER Utente con id: " + userId + " eliminato con successo!::");
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

    private boolean noFieldsToUpdate(UserUpdateDto dto) {
        return (dto.getFirstName() == null || dto.getFirstName().trim().isEmpty()) &&
                (dto.getLastName() == null || dto.getLastName().trim().isEmpty()) &&
                dto.getBirthDate() == null;
    }
}
