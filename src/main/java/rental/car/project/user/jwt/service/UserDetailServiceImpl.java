package rental.car.project.user.jwt.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import rental.car.project.user.domain.User;
import rental.car.project.user.infrastructure.UserRepository;
import rental.car.project.user.jwt.data.JwtUser;

import java.util.Optional;


@Component
public class UserDetailServiceImpl implements UserDetailsService {

    private static final Logger logger = LogManager.getLogger(UserDetailServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info(":: UserDetailServiceImpl.loadUsername (START) :: Username: " + username);

        Optional<User> optionalUser = userRepository.findByUsername(username);
        optionalUser.orElseThrow(() -> new UsernameNotFoundException("Username " + username + " non trovato!"));

        JwtUser jwtUser = new JwtUser(optionalUser.get());

        logger.info(":: UserDetailServiceImpl.loadUsername (END) :: JwtUser: " + jwtUser);
        return jwtUser;
    }
}
