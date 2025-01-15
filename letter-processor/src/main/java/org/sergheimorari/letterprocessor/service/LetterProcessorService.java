package org.sergheimorari.letterprocessor.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sergheimorari.letterprocessor.model.Letter;
import org.sergheimorari.letterprocessor.repository.LetterRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LetterProcessorService {
  private final LetterRepository letterRepository;

  public Page<Letter> getLettersByEmail(String email, Pageable pageable) {
    log.info("Looking for letters by email: {}", email);
    return letterRepository.findLettersByEmail(email, pageable);
  }

  public Page<Letter> getAllLetters(Pageable pageable) {
    return letterRepository.findAll(pageable);
  }
}
