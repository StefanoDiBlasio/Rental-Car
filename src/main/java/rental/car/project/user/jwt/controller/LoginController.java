package rental.car.project.user.jwt.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import rental.car.project.user.jwt.configuration.JwtUtil;
import rental.car.project.user.jwt.dto.LoginRequestDto;
import rental.car.project.user.jwt.dto.LoginResponseDto;
import rental.car.project.user.jwt.service.AuthenticationService;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<LoginResponseDto> authenticate(@RequestBody LoginRequestDto loginRequestDTO) throws Exception {
        logger.info("::LoginController.authenticate (START):: loginRequestDTO: " + loginRequestDTO.toString());

        UserDetails userPrincipal = authenticationService.authenticate(loginRequestDTO);
        logger.info("::LoginController.authenticate:: UserDetails: " + userPrincipal.toString());

        String tokenJWT = JwtUtil.generateToken(userPrincipal);
        logger.info("::LoginController.authenticate:: TokenJWT: " + tokenJWT);

        LoginResponseDto loginResponseDto = new LoginResponseDto(loginRequestDTO.getUsername(), tokenJWT);
        logger.info("::LoginController.authenticate:: LoginResponseDTO: " + loginResponseDto + "]");

        logger.info("::LoginController.authenticate (END)");
        return ResponseEntity.ok().body(loginResponseDto);
    }
}
