package org.sergheimorari.letterprocessor.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory;
import lombok.extern.slf4j.Slf4j;
import org.sergheimorari.letterprocessor.util.SqsMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@Slf4j
@Configuration
public class SqsConfiguration {

  @Bean
  public SqsMessageListenerContainerFactory<Object> sqsMessageListenerContainerFactory(
      SqsAsyncClient sqsAsyncClient, ObjectMapper objectMapper) {

    return SqsMessageListenerContainerFactory.builder()
        .sqsAsyncClient(sqsAsyncClient)
        .configure(builder -> builder.messageConverter(new SqsMessageConverter(objectMapper)))
        .build();
  }

  @Bean
  public ObjectMapper customObjectMapper() {
    return new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }
}
