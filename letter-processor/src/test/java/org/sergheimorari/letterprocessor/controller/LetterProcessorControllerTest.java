package org.sergheimorari.letterprocessor.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.sergheimorari.letterprocessor.model.Letter;
import org.sergheimorari.letterprocessor.repository.LetterRepository;
import org.sergheimorari.letterprocessor.utils.LocalStackTestContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.sergheimorari.letterprocessor.utils.LetterProcessorTestUtils.createTestAddress;
import static org.sergheimorari.letterprocessor.utils.LetterProcessorTestUtils.createTestLetter;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:config-test.properties")
class LetterProcessorControllerTest extends LocalStackTestContainer {

  private static final String LETTER_API_PATH = "/api/v1/christmas-letters";

  @Autowired private MockMvc mockMvc;
  @Autowired private LetterRepository letterRepository;

  private static Stream<Arguments> provideLetters() {
    return Stream.of(
        Arguments.of(PageRequest.of(0, 10), getNewLetters(1)),
        Arguments.of(PageRequest.of(1, 2), getNewLetters(3)));
  }

  private static List<Letter> getNewLetters(int count) {
    return IntStream.range(0, count).mapToObj(el -> createTestLetter(createTestAddress())).toList();
  }

  @BeforeEach
  void setup() {
    letterRepository.deleteAll();
  }

  @ParameterizedTest
  @MethodSource("provideLetters")
  void givenPage_whenGetMethod_thenReturnPageLetters(Pageable pageable, List<Letter> letters)
      throws Exception {
    // given
    letterRepository.saveAll(letters);

    // when
    mockMvc
        .perform(
            get(LETTER_API_PATH)
                .param("page", String.valueOf(pageable.getPageNumber()))
                .param("size", String.valueOf(pageable.getPageSize())))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content").isArray())
        .andExpect(jsonPath("$.totalElements").value(letters.size()))
        .andExpect(jsonPath("$.pageable.pageSize").value(pageable.getPageSize()))
        .andExpect(jsonPath("$.pageable.pageNumber").value(pageable.getPageNumber()))
        .andExpect(jsonPath("$.pageable.offset").value(pageable.getOffset()));
  }

  @Test
  void givenInvalidEmail_whenGetByEmail_thenThrowValidationException() throws Exception {
    // given
    var email = "invalid_email";

    // when
    mockMvc
        .perform(get(String.format("%s/{email}", LETTER_API_PATH), email))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Validation failure"))
        .andExpect(jsonPath("$.errors[0].email").value("Invalid email"));
  }
}
