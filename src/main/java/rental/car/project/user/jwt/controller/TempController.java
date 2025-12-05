package rental.car.project.user.jwt.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class TempController {

    private static final Logger logger = LoggerFactory.getLogger(TempController.class);

    @GetMapping("/temp")
    public String temp() {
        logger.info("::TempController.temp::");
        return "::Accesso senza autenticazione::";

    }
}
