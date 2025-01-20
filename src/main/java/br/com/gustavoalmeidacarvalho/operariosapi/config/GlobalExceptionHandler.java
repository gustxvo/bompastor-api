package br.com.gustavoalmeidacarvalho.operariosapi.config;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.exception.UserConflictException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserConflictException.class)
    private ProblemDetail handleUserConflictException(UserConflictException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());
        problemDetail.setInstance(URI.create("user"));
        return problemDetail;
    }

}
