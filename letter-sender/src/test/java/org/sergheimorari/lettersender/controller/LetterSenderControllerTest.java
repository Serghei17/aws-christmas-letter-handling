package org.sergheimorari.lettersender.controller;

import static org.mockito.Mockito.doThrow;
import static org.sergheimorari.lettersender.SendLetterTestUtils.createTestAddress;
import static org.sergheimorari.lettersender.SendLetterTestUtils.createTestLetter;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.sergheimorari.lettersender.model.Letter;
import org.sergheimorari.lettersender.service.LetterSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.messaging.MessagingException;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(LetterSenderController.class)
class LetterSenderControllerTest {

  private static final String CREATE_LETTER_URL = "/api/v1/christmas-letter-sender";

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  @MockBean private LetterSenderService letterSenderService;

  @BeforeEach
  void setUp() {}

  @Test
  void sendLetterIsValid_ShouldReturn201() throws Exception {
    // given
    var address = createTestAddress();
    var letter = createTestLetter(address);

    // when
    mockMvc
        .perform(
            post(CREATE_LETTER_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(letter)))
        .andExpect(status().is2xxSuccessful());
  }

  @ParameterizedTest
  @MethodSource("org.sergheimorari.lettersender.SendLetterTestUtils#provideInvalidLetters")
  void sendLetterIsInvalid(Letter letter, String expectedOutput) throws Exception {

    // when
    mockMvc
        .perform(
            post(CREATE_LETTER_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(letter)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Validation failed!"))
        .andExpect(jsonPath("$.details[0]").value(expectedOutput));
  }

  @Test
  void sendLetterFailed_ShouldThrowMessagingException() throws Exception {
    // given
    var letter = createTestLetter(createTestAddress());

    doThrow(MessagingException.class).when(letterSenderService).send(letter);

    // when
    mockMvc
        .perform(
            post(CREATE_LETTER_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(letter)))
        .andExpect(status().isInternalServerError())
        .andExpect(jsonPath("$.message").value("Publishing message to SNS failed!"));
  }
}
