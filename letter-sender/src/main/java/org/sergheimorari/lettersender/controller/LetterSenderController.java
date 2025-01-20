package org.sergheimorari.lettersender.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sergheimorari.lettersender.model.Letter;
import org.sergheimorari.lettersender.service.LetterSenderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/christmas-letter-sender")
@RequiredArgsConstructor
public class LetterSenderController {
  private final LetterSenderService letterSenderService;

  @PostMapping
  ResponseEntity<Object> sendLetter(@Valid @RequestBody Letter letter) {
    log.info("Sending letter: {}", letter);
    letterSenderService.send(letter);

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
}
