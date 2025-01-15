package org.sergheimorari.letterprocessor.exceptionhandling;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessagingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

  @ExceptionHandler(HandlerMethodValidationException.class)
  public ResponseEntity<ErrorResponse> handleMethodValidationException(
      final HandlerMethodValidationException exception) {
    List<Map<String, String>> errorList =
        exception.getAllErrors().stream().map(this::getValidations).toList();

    return ResponseEntity.badRequest().body(new ErrorResponse("Validation Failed!", errorList));
  }

  @ExceptionHandler(MessagingException.class)
  private Map<String, String> getValidations(MessageSourceResolvable exception) {
    var error = new HashMap<String, String>();
    var parameterValue =
        ((MessageSourceResolvable) Objects.requireNonNull(exception.getArguments())[0])
            .getDefaultMessage();
    error.put(parameterValue, exception.getDefaultMessage());

    log.error("Error processing request: {}", exception.getDefaultMessage());

    return error;
  }
}
