package rental.car.project.user.jwt.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import rental.car.project.user.domain.RoleType;
import rental.car.project.user.domain.User;
import rental.car.project.user.domain.UserDto;
import rental.car.project.user.infrastructure.UserRepository;
import rental.car.project.user.jwt.configuration.JwtUtil;
import rental.car.project.user.jwt.dto.LoginRequestDto;
import rental.car.project.user.jwt.dto.LoginResponseDto;
import rental.car.project.user.jwt.dto.RegistrationRequestDto;
import rental.car.project.user.mapper.RegistrationMapper;
import rental.car.project.user.mapper.UserMapper;

import java.util.Optional;

@Service
public class AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RegistrationMapper registrationMapper;

    @Autowired
    private UserMapper userMapper;

    public UserDto createUser(RegistrationRequestDto dto, RoleType roleType) {

        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("::Username già presente!::");
        }

        User user = registrationMapper.convertToEntity(dto);
        user.setRoleType(roleType);
        user.setEnabled(true);
        user = userRepository.save(user);

        return userMapper.convertToDto(user);
    }

    private UserDetails authenticate(LoginRequestDto loginRequestDto) {
        logger.info("::AuthenticationService.authenticate:: (START)");

        Authentication authentication;
        try {
            UsernamePasswordAuthenticationToken authObject = new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword());
            authentication = authenticationManager.authenticate(authObject);
        } catch (BadCredentialsException e) {
            logger.error("::AuthenticationService.authenticate:: Bad Credentials: " + e);
            throw e;
        }

        logger.info(":: User autenticato con successo! ::");
        logger.info("::AuthenticationService.authenticate:: (END)");
        return (UserDetails) authentication.getPrincipal();
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        UserDetails userDetails = authenticate(loginRequestDto);
        logger.info("::LoginController.authenticate:: UserDetails: " + userDetails.toString());

        Optional<User> user = userRepository.findByUsername(loginRequestDto.getUsername());
        String role = user.get().getRoleType().name();

        String tokenJWT = JwtUtil.generateToken(userDetails, role);
        logger.info("::LoginController.authenticate:: TokenJWT: " + tokenJWT);

        LoginResponseDto loginResponseDto = new LoginResponseDto(loginRequestDto.getUsername(), tokenJWT);
        logger.info("::LoginController.authenticate:: LoginResponseDTO: " + loginResponseDto + "]");

        return loginResponseDto;
    }
}
