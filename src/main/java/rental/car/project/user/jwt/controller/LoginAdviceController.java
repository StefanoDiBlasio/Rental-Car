package rental.car.project.user.jwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import rental.car.project.user.jwt.dto.ErrorResponseDto;

/**
 * Intercetta le eccezioni nei controller (dopo aver superato la FilterChain)
 */

@ControllerAdvice
public class LoginAdviceController extends ResponseEntityExceptionHandler {

    //eseguito ogni volta che viene lanciata una BadCredentialsException
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDto> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        ErrorResponseDto responseDTO = ErrorResponseDto.builder()
                .code(401)
                .message("Bad credentials!")
                .build();

        logger.error("::LoginAdviceController.handleBadCredentialsException:: BAD CREDENTIALS: " + ex);

        return ResponseEntity.status(401).body(responseDTO);
    }
}
