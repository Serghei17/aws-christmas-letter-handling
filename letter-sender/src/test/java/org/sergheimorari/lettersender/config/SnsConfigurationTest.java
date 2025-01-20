package org.sergheimorari.lettersender.config;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = SnsConfiguration.class)
@EnableConfigurationProperties(SnsConfiguration.class)
@TestPropertySource("classpath:application-test.yml")
public class SnsConfigurationTest {

  @Autowired private SnsConfiguration snsConfiguration;

  @Test
  void givenProperty_whenBinding_ThenTopicArnIsSet() {
    assertThat(snsConfiguration.getTopicArn()).isEqualTo("sns-topic");
  }
}
