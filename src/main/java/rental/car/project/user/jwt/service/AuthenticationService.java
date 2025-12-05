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
import rental.car.project.user.jwt.dto.LoginRequestDto;

@Service
public class AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    public UserDetails authenticate(LoginRequestDto loginRequestDto) throws Exception {
        logger.info("::AuthenticationService.authenticate:: (START)");

        Authentication authentication = null;
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
}
