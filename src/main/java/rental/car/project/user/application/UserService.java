package rental.car.project.user.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rental.car.project.user.infrastructure.UserRepository;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    public Optional<>

}
