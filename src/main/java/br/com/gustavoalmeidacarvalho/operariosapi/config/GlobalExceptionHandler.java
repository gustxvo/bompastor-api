package br.com.gustavoalmeidacarvalho.operariosapi.config;

import br.com.gustavoalmeidacarvalho.operariosapi.domain.exception.ExpiredRefreshTokenException;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.exception.InvalidUserCredentials;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.exception.ResourceNotFoundException;
import br.com.gustavoalmeidacarvalho.operariosapi.domain.exception.UserConflictException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

import static br.com.gustavoalmeidacarvalho.operariosapi.domain.auth.RefreshToken.REFRESH_TOKEN;
import static br.com.gustavoalmeidacarvalho.operariosapi.domain.user.User.USER;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String AUTH = "Authentication";

    @ExceptionHandler(IllegalArgumentException.class)
    private ProblemDetail handleIllegalArgumentException(IllegalArgumentException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setTitle("Invalid Argument");
        return problemDetail;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    private ProblemDetail handleResourceNotFound(ResourceNotFoundException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setType(URI.create(e.getType()));
        return problemDetail;
    }

    @ExceptionHandler(UserConflictException.class)
    private ProblemDetail handleUserConflictException(UserConflictException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());
        problemDetail.setInstance(URI.create(USER));
        return problemDetail;
    }

    @ExceptionHandler(InvalidUserCredentials.class)
    private ProblemDetail handleInvalidCredentials(InvalidUserCredentials e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, e.getMessage());
        problemDetail.setType(URI.create(AUTH));
        problemDetail.setInstance(URI.create(USER));
        return problemDetail;
    }

    @ExceptionHandler(ExpiredRefreshTokenException.class)
    private ProblemDetail handleExpiredRefreshTokenException(ExpiredRefreshTokenException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());
        problemDetail.setType(URI.create(AUTH));
        problemDetail.setInstance(URI.create(REFRESH_TOKEN));
        return problemDetail;
    }

}
