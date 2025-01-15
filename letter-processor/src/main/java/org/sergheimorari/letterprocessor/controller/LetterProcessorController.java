package org.sergheimorari.letterprocessor.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sergheimorari.letterprocessor.model.Letter;
import org.sergheimorari.letterprocessor.service.LetterProcessorService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("/api/v1/christmas-letters")
@RequiredArgsConstructor
public class LetterProcessorController {

  private final LetterProcessorService letterProcessorService;

  @GetMapping("/{email}")
  Page<Letter> getLettersByEmail(
      @PathVariable @Valid @Email(message = "Invalid email") String email, int page, int size) {
    log.info("Looking for letters by email: {}", email);
    return letterProcessorService.getLettersByEmail(
        email, org.springframework.data.domain.PageRequest.of(page, size));
  }

  Page<Letter> getAllEmails(int page, int size) {
    log.info("Retrieving all emails by page: {}, size: {}", page, size);
    return letterProcessorService.getAllLetters(
        org.springframework.data.domain.PageRequest.of(page, size));
  }
}
