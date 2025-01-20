package org.sergheimorari.letterprocessor.listener;

import static org.sergheimorari.letterprocessor.util.LetterConverter.letterDtoToLetter;

import io.awspring.cloud.sqs.annotation.SqsListener;
import jakarta.validation.Valid;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sergheimorari.letterprocessor.dto.LetterDto;
import org.sergheimorari.letterprocessor.repository.LetterRepository;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SqsLetterListener {

  private final LetterRepository letterRepository;

  @SqsListener(value = "${letter-processor.aws.sqs.queue.url}")
  public void receiveLetter(@Payload @Valid LetterDto letterDto) {
    log.info("Received letter: {} on: {}", letterDto, OffsetDateTime.now());

    letterRepository.save(letterDtoToLetter(letterDto));

    log.info("Successfully saved letter: {} on: {}", letterDto, OffsetDateTime.now());
  }
}
