package org.sergheimorari.lettersender.service;

import static org.mockito.Mockito.*;
import static org.sergheimorari.lettersender.utils.SendLetterTestUtils.createTestAddress;
import static org.sergheimorari.lettersender.utils.SendLetterTestUtils.createTestLetter;

import io.awspring.cloud.sns.core.SnsTemplate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sergheimorari.lettersender.config.SnsConfiguration;

@ExtendWith(MockitoExtension.class)
class LetterSenderServiceTest {

  @Mock private SnsTemplate snsTemplate;
  @Mock private SnsConfiguration snsConfiguration;

  @InjectMocks private LetterSenderService letterSenderService;

  @Test
  void send() {
    // given
    var address = createTestAddress();
    var letter = createTestLetter(address);

    var topicArn = "create-letter-topic";
    when(snsConfiguration.getTopicArn()).thenReturn(topicArn);

    // when
    letterSenderService.send(letter);

    // then
    verify(snsTemplate, times(1)).convertAndSend(topicArn, letter);
  }
}
