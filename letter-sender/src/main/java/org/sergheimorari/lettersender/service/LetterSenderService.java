package org.sergheimorari.lettersender.service;

import io.awspring.cloud.sns.core.SnsTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sergheimorari.lettersender.config.SnsConfiguration;
import org.sergheimorari.lettersender.model.Letter;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LetterSenderService {
  private final SnsTemplate snsTemplate;
  private final SnsConfiguration snsConfiguration;

  public void send(Letter letter) {
    log.info("Sending letter: {}", letter);

    final var topicArn = snsConfiguration.getTopicArn();
    snsTemplate.convertAndSend(topicArn, letter);

    log.info("Successfully published letter: {} to topicArn: {}", letter, topicArn);
  }
}
