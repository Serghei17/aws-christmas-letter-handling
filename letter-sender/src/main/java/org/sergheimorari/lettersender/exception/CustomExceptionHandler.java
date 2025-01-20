package org.sergheimorari.lettersender.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.MessagingException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationException(
      final MethodArgumentNotValidException exception) {
    var details =
        exception.getBindingResult().getAllErrors().stream()
            .map(MessageSourceResolvable::getDefaultMessage)
            .toList();

    return ResponseEntity.badRequest().body(new ErrorResponse("Validation failed!", details));
  }

  @ExceptionHandler(MessagingException.class)
  public ResponseEntity<ErrorResponse> handleMessageException(final MessagingException exception) {
    log.error("Error processing request: {}", exception.getMessage(), exception);

    return ResponseEntity.internalServerError()
        .body(
            new ErrorResponse(
                "Publishing message to SNS failed!",
                Collections.singletonList(exception.getMessage())));
  }
}
