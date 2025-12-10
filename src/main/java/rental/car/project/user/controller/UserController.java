package rental.car.project.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.DisabledException;
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
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('SUPERADMIN')")
    @GetMapping(path = "/users")
    public ResponseEntity<?> getAll() {
        try{
            logger.info("::UserController.getAll (START)::");
            List<UserDto> users = userService.getAllUsers();
            logger.info("::UserController.getAll (END)::");
            return ResponseEntity.ok().body(users);
        } catch (DisabledException ex) {
            logger.error("::UserController.getAll - DisabledException:: L'utente e' disabilitato!" );
            return ResponseEntity.internalServerError().body("::ERRORE NELL' AUTENTICAZIONE DELL'UTENTE:: L' UTENTE E' DISABILITATO!");
        }catch (Exception e){
            logger.error("::UserController.getAll - Internal Server Error:: " + e);
            return ResponseEntity.internalServerError().body("::ERRORE GET_ALL:: " + e);
        }

    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @GetMapping(path = "/{userId}")
    public ResponseEntity<?> get(@PathVariable(value = "userId") Long userId) {
        logger.info("::UserController.get (START)::");
        try{
            UserDto user = userService.getUserById(userId);
            logger.info("::UserController.get (END)::");
            return ResponseEntity.ok().body(user);
        } catch (DisabledException ex) {
            logger.error("::UserController.authenticate - DisabledException:: L'utente con Id " + userId + " e' disabilitato!" );
            return ResponseEntity.internalServerError().body("::ERRORE NELL' AUTENTICAZIONE DELL'UTENTE:: L' UTENTE CON ID " + userId + " E' DISABILITATO!");
        } catch (Exception e) {
            logger.error("::UserController.get - Internal Server Error:: " + e);
            return ResponseEntity.internalServerError().body("::ERRORE GET_BY_ID:: " + e);
        }
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @PostMapping(value = "/register/admin")
    public ResponseEntity<?> registerAdmin(@RequestBody RegistrationRequestDto registrationRequestDto) {
        logger.info("::UserController.registerAdmin (START):: registerRequestDTO: " + registrationRequestDto.toString());

        try {
            UserDto dto = userService.createUser(registrationRequestDto, RoleType.SUPERADMIN);
            logger.info("::Registrazione dell'utente Admin" + registrationRequestDto.getFirstName() + " " + registrationRequestDto.getLastName() + " avvenuta con successo!::");
            logger.info("::UserController.registerAdmin (END)::");
            return ResponseEntity.ok().body(dto);
        } catch (IllegalArgumentException ex) {
            logger.error("::UserController.registerAdmin - IllegalArgumentException:: " + ex );
            return ResponseEntity.internalServerError().body("::ERRORE NELLA REGISTRAZIONE DELL'ADMIN: " + registrationRequestDto.getFirstName() + " " + registrationRequestDto.getLastName() + ". Utente già presente!");
        } catch (DisabledException ex) {
            logger.error("::UserController.registerAdmin - DisabledException:: L'utente " + registrationRequestDto.getUsername() + " e' disabilitato!" );
            return ResponseEntity.internalServerError().body("::ERRORE NELL' AUTENTICAZIONE DELL'UTENTE:: L' UTENTE " + registrationRequestDto.getUsername() + " E' DISABILITATO!");
        } catch (Exception e) {
            logger.error("::UserController.registerAdmin - Internal Server Error:: " + e);
            return ResponseEntity.internalServerError().body("::ERRORE NELLA REGISTRAZIONE DELL'ADMIN: " + registrationRequestDto.getFirstName() + " " + registrationRequestDto.getLastName());
        }
    }

    @PostMapping(value = "/register/customer")
    public ResponseEntity<?> registerCustomer(@RequestBody RegistrationRequestDto registrationRequestDto) {
        logger.info("::UserController.registerCustomer (START):: registerRequestDTO: " + registrationRequestDto.toString());

        try {
            UserDto dto = userService.createUser(registrationRequestDto, RoleType.CUSTOMER);
            logger.info("::Registrazione dell'utente customer " + registrationRequestDto.getFirstName() + " " + registrationRequestDto.getLastName() + " avvenuta con successo!::");
            logger.info("::UserController.registerCustomer (END)::");
            return ResponseEntity.ok().body(dto);
        } catch (IllegalArgumentException ex) {
            logger.error("::UserController.registerCustomer - IllegalArgumentException:: " + ex );
            return ResponseEntity.internalServerError().body("::ERRORE NELLA REGISTRAZIONE DEL CUSTOMER: " + registrationRequestDto.getFirstName() + " " + registrationRequestDto.getLastName() + ". Utente già presente!");
        } catch (Exception e) {
            logger.error("::UserController.registerCustomer - Internal Server Error:: " + e);
            return ResponseEntity.internalServerError().body("::ERRORE NELLA REGISTRAZIONE DEL CUSTOMER: " + registrationRequestDto.getFirstName() + " " + registrationRequestDto.getLastName());
        }
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @PutMapping(path = "/{userId}")
    public ResponseEntity<?> update(@PathVariable(value = "userId") Long userId, @RequestBody UserUpdateDto updateDto) {
        logger.info("::UserController.update (START)::");

        try{
            UserDto userDto = userService.updateUser(userId, updateDto);
            logger.info("::Aggiornamento dell'utente " + userDto.getFirstName() + " " + userDto.getLastName() + " avvenuto con successo!::");
            logger.info("::UserController.update (END)::");
            return ResponseEntity.ok().body(userDto);
        } catch (DisabledException ex) {
            logger.error("::UserController.update - DisabledException:: L'utente con Id " + userId + " e' disabilitato!" );
            return ResponseEntity.internalServerError().body("::ERRORE NELL' AUTENTICAZIONE DELL'UTENTE:: L' UTENTE CON ID " + userId + " E' DISABILITATO!");
        } catch (Exception e) {
            logger.error("::UserController.update - Internal Server Error:: " + e);
            return ResponseEntity.internalServerError().body("::ERRORE NELL'AGGIORNAMENTO DELL'UTENTE:: ID: " + userId);
        }
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @DeleteMapping(path = "{userId}")
    public ResponseEntity<?> delete(@PathVariable(value = "userId") Long userId) {
        logger.info("::UserController.delete (START)::");
        try{
            userService.deleteUser(userId);
            return ResponseEntity.noContent().build();
        } catch (DisabledException ex) {
            logger.error("::UserController.delete - DisabledException:: L'utente con Id " + userId + " e' disabilitato!" );
            return ResponseEntity.internalServerError().body("::ERRORE NELL' AUTENTICAZIONE DELL'UTENTE:: L' UTENTE CON ID " + userId + " E' DISABILITATO!");
        } catch (Exception e) {
            logger.error("::UserController.delete - Internal Server Error:: " + e);
            return ResponseEntity.internalServerError().body("::ERRORE NELL'ELIMINAZIONE DELL'UTENTE:: ID: " + userId);
        }
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody LoginRequestDto loginRequestDTO) {
        logger.info("::UserController.authenticate (START):: loginRequestDTO: " + loginRequestDTO.toString());

        try {
            LoginResponseDto responseDto = authenticationService.login(loginRequestDTO);
            logger.info("::UserController.authenticate (END)");
            return ResponseEntity.ok().body(responseDto);
        } catch (DisabledException ex) {
            logger.error("::UserController.authenticate - DisabledException:: L'utente " + loginRequestDTO.getUsername() + " e' disabilitato!" );
            return ResponseEntity.internalServerError().body("::ERRORE NELL' AUTENTICAZIONE DELL'UTENTE:: L' UTENTE " + loginRequestDTO.getUsername() + " E' DISABILITATO!");
        } catch (Exception e) {
            logger.error("::UserController.authenticate - Internal Server Error:: " + e);
            return ResponseEntity.internalServerError().body("::ERRORE NELL' AUTENTICAZIONE DELL'UTENTE: " + loginRequestDTO.getUsername());
        }
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @PutMapping(path = "/enable/{userId}")
    public ResponseEntity<?> enable(@PathVariable(value = "userId") Long userId) {
        logger.info("::UserController.enable (START)::");
        try{
            userService.setEnabled(userId);
            logger.info("::UserController.enable (END)::");
            return ResponseEntity.ok().body("::UserController.enable:: Utente abilitato!");
        } catch (DisabledException ex) {
            logger.error("::UserController.enable - DisabledException:: L'utente con Id " + userId + " e' disabilitato!" );
            return ResponseEntity.internalServerError().body("::ERRORE NELL' ABILITAZIONE DELL'UTENTE:: L' UTENTE CON ID " + userId + " E' DISABILITATO!");
        } catch (Exception e) {
            logger.error("::UserController.enable - Internal Server Error:: " + e);
            return ResponseEntity.internalServerError().body("::ERRORE NELL'ABILITAZIONE DELL'UTENTE:: ID: " + userId);
        }
    }

    @PreAuthorize("hasRole('SUPERADMIN')")
    @PutMapping(path = "/disable/{userId}")
    public ResponseEntity<?> disable(@PathVariable(value = "userId") Long userId) {
        logger.info("::UserController.disable (START)::");
        try{
            userService.setDisabled(userId);
            logger.info("::UserController.disable (END)::");
            return ResponseEntity.ok().body("::UserController.disable:: Utente disabilitato!");
        } catch (DisabledException ex) {
            logger.error("::UserController.disable - DisabledException:: L'utente con Id " + userId + " e' disabilitato!" );
            return ResponseEntity.internalServerError().body("::ERRORE NELLA DISABILITAZIONE DELL'UTENTE:: L' UTENTE CON ID " + userId + " E' DISABILITATO!");
        } catch (Exception e) {
            logger.error("::UserController.disable - Internal Server Error:: " + e);
            return ResponseEntity.internalServerError().body("::ERRORE NELLA DISABILITAZIONE DELL'UTENTE:: ID: " + userId);
        }
    }
}