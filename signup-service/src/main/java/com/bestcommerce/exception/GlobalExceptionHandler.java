package com.bestcommerce.exception;

import com.bestcommerce.response.ExceptionResponse;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(EmailAlreadyExistsException.class)
  public final ResponseEntity<ExceptionResponse> handleNotFoundException(
      EmailAlreadyExistsException ex, WebRequest request) {
    ExceptionResponse exceptionResponse =
        new ExceptionResponse(ex.getMessage(), request.getDescription(false));
    return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public final ResponseEntity<ExceptionResponse> handleValidationException(
      ConstraintViolationException ex, WebRequest request) {
    ExceptionResponse exceptionResponse =
        new ExceptionResponse(ex.getMessage(), request.getDescription(false));
    return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_ACCEPTABLE);
  }

  @ExceptionHandler(FormValidationException.class)
  public final ResponseEntity<ExceptionResponse> handleValidationException(
      FormValidationException ex, WebRequest request) {
    ExceptionResponse exceptionResponse =
        new ExceptionResponse(ex.getMessage(), request.getDescription(false));
    return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_ACCEPTABLE);
  }

  @ExceptionHandler(AuthenticationException.class)
  public final ResponseEntity<ExceptionResponse> handleValidationException(
      AuthenticationException ex, WebRequest request) {
    ExceptionResponse exceptionResponse =
        new ExceptionResponse(ex.getMessage(), request.getDescription(false));
    return new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);
  }
}
