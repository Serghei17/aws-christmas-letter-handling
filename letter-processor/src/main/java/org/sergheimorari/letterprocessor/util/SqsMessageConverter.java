package org.sergheimorari.letterprocessor.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.support.converter.SqsMessagingMessageConverter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sergheimorari.letterprocessor.exceptionhandling.LetterDeserializationException;
import software.amazon.awssdk.services.sqs.model.Message;

@Slf4j
@RequiredArgsConstructor
public class SqsMessageConverter extends SqsMessagingMessageConverter {

  private final ObjectMapper objectMapper;

  @Override
  @NonNull
  protected Object getPayloadToDeserialize(@NonNull Message message) {
    log.info("Converting message: {}", message);

    var body = message.body();
    return unwrapMessage(body);
  }

  private String unwrapMessage(String message) {
    try {
      var jsonNode = objectMapper.readTree(message);
      if (jsonNode.has("Message")) {
        return jsonNode.get("Message").asText();
      } else throw new LetterDeserializationException("Invalid message format!");
    } catch (JsonProcessingException e) {
      log.error(e.getMessage(), e);
      throw new LetterDeserializationException("Invalid message format!");
    }
  }
}
