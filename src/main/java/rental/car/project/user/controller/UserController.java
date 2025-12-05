package rental.car.project.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import rental.car.project.user.domain.RoleType;
import rental.car.project.user.domain.User;
import rental.car.project.user.domain.UserDto;
import rental.car.project.user.infrastructure.UserRepository;
import rental.car.project.user.jwt.configuration.JwtUtil;
import rental.car.project.user.jwt.dto.LoginRequestDto;
import rental.car.project.user.jwt.dto.LoginResponseDto;
import rental.car.project.user.jwt.dto.RegistrationRequestDto;
import rental.car.project.user.jwt.service.AuthenticationService;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = "/register/admin")
    public ResponseEntity<?> registerAdmin(@RequestBody RegistrationRequestDto registrationRequestDto) {
        logger.info("::LoginController.registeraAdmin (START):: registerRequestDTO: " + registrationRequestDto.toString());

        try{
            UserDto dto = authenticationService.createUser(registrationRequestDto, RoleType.SUPERADMIN);
            logger.info("::Registrazione dell'utente Admin" + registrationRequestDto.getFirstName() + " " + registrationRequestDto.getLastName() + " avvenuta con successo!::");
            logger.info("::LoginController.register (END)::");
            return ResponseEntity.ok().body(dto);
        } catch (Exception e) {
            logger.error("::Internal Server Error:: " + e);
            return ResponseEntity.internalServerError().body("::ERRORE NELLA REGISTRAZIONE DELL'UTENTE: " + registrationRequestDto.getFirstName() + " " + registrationRequestDto.getLastName());
        }
    }

    @PostMapping(value = "/register/customer")
    public ResponseEntity<?> registerCustomer(@RequestBody RegistrationRequestDto registrationRequestDto) {
        logger.info("::LoginController.registerCustomer (START):: registerRequestDTO: " + registrationRequestDto.toString());

        try{
            UserDto dto = authenticationService.createUser(registrationRequestDto, RoleType.CUSTOMER);
            logger.info("::Registrazione dell'utente customer " + registrationRequestDto.getFirstName() + " " + registrationRequestDto.getLastName() + " avvenuta con successo!::");
            logger.info("::LoginController.register (END)::");
            return ResponseEntity.ok().body(dto);
        } catch (Exception e) {
            logger.error("::Internal Server Error:: " + e);
            return ResponseEntity.internalServerError().body("::ERRORE NELLA REGISTRAZIONE DELL'UTENTE: " + registrationRequestDto.getFirstName() + " " + registrationRequestDto.getLastName());
        }
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody LoginRequestDto loginRequestDTO) throws Exception {
        logger.info("::LoginController.authenticate (START):: loginRequestDTO: " + loginRequestDTO.toString());

        try{
            LoginResponseDto responseDto = authenticationService.login(loginRequestDTO);
            logger.info("::LoginController.authenticate (END)");
            return ResponseEntity.ok().body(responseDto);
        } catch (Exception e) {
            logger.error("::Internal Server Error:: " + e);
            return ResponseEntity.internalServerError().body("::ERRORE NELL' AUTENTICAZIONE DELL'UTENTE: " + loginRequestDTO.getUsername());
        }



    }
}