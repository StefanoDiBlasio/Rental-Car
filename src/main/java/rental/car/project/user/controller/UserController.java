package rental.car.project.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rental.car.project.user.application.UserService;
import rental.car.project.user.domain.RoleType;
import rental.car.project.user.dto.UserDto;
import rental.car.project.user.dto.UserUpdateDto;
import rental.car.project.user.jwt.dto.LoginRequestDto;
import rental.car.project.user.jwt.dto.LoginResponseDto;
import rental.car.project.user.jwt.dto.RegistrationRequestDto;
import rental.car.project.user.jwt.service.AuthenticationService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin("*")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('SUPERADMIN')")
    @GetMapping(path = "/all")
    public ResponseEntity<List<UserDto>> getAll() {
        logger.info("::UserController.getAll (START)::");
        List<UserDto> users = userService.getAllUsers();
        logger.info("::UserController.getAll (END)::");
        return ResponseEntity.ok().body(users);
    }

    @GetMapping(path = "/{userId}")
    public ResponseEntity<UserDto> get(@PathVariable(value = "userId") Long userId) {
        logger.info("::UserController.get (START)::");
        UserDto user = userService.getUserById(userId);
        logger.info("::UserController.get (END)::");
        return ResponseEntity.ok().body(user);
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @PostMapping(value = "/register/admin")
    public ResponseEntity<UserDto> registerAdmin(@RequestBody RegistrationRequestDto registrationRequestDto) {
        logger.info("::UserController.registerAdmin (START):: registerRequestDTO: " + registrationRequestDto.toString());
        UserDto dto = userService.createUser(registrationRequestDto, RoleType.SUPERADMIN);
        logger.info("::Registrazione dell'utente Admin" + registrationRequestDto.getFirstName() + " " + registrationRequestDto.getLastName() + " avvenuta con successo!::");
        logger.info("::UserController.registerAdmin (END)::");
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping(value = "/register/customer")
    public ResponseEntity<UserDto> registerCustomer(@RequestBody RegistrationRequestDto registrationRequestDto) {
        logger.info("::UserController.registerCustomer (START):: registerRequestDTO: " + registrationRequestDto.toString());
        UserDto dto = userService.createUser(registrationRequestDto, RoleType.CUSTOMER);
        logger.info("::Registrazione dell'utente customer " + registrationRequestDto.getFirstName() + " " + registrationRequestDto.getLastName() + " avvenuta con successo!::");
        logger.info("::UserController.registerCustomer (END)::");
        return ResponseEntity.ok().body(dto);
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @PutMapping(path = "/{userId}")
    public ResponseEntity<UserDto> update(@PathVariable(value = "userId") Long userId, @RequestBody UserUpdateDto updateDto) {
        logger.info("::UserController.update (START)::");
        UserDto userDto = userService.updateUser(userId, updateDto);
        logger.info("::Aggiornamento dell'utente " + userDto.getFirstName() + " " + userDto.getLastName() + " avvenuto con successo!::");
        logger.info("::UserController.update (END)::");
        return ResponseEntity.ok().body(userDto);
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @DeleteMapping(path = "{userId}")
    public ResponseEntity<?> delete(@PathVariable(value = "userId") Long userId) {
        logger.info("::UserController.delete (START)::");
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<LoginResponseDto> authenticate(@RequestBody LoginRequestDto loginRequestDTO) {
        logger.info("::UserController.authenticate (START):: loginRequestDTO: " + loginRequestDTO.toString());
        LoginResponseDto responseDto = authenticationService.login(loginRequestDTO);
        logger.info("::UserController.authenticate (END)");
        return ResponseEntity.ok().body(responseDto);
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @PutMapping(path = "/enable/{userId}")
    public ResponseEntity<?> enable(@PathVariable(value = "userId") Long userId) {
        logger.info("::UserController.enable (START)::");
        userService.setEnabled(userId);
        logger.info("::UserController.enable (END)::");
        return ResponseEntity.ok().body("::UserController.enable:: Utente abilitato!");
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @PutMapping(path = "/disable/{userId}")
    public ResponseEntity<?> disable(@PathVariable(value = "userId") Long userId) {
        logger.info("::UserController.disable (START)::");
        userService.setDisabled(userId);
        logger.info("::UserController.disable (END)::");
        return ResponseEntity.ok().body("::UserController.disable:: Utente disabilitato!");
    }
}