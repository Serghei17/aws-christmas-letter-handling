package org.sergheimorari.letterprocessor.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.sergheimorari.letterprocessor.utils.LetterProcessorTestUtils.createTestAddress;
import static org.sergheimorari.letterprocessor.utils.LetterProcessorTestUtils.createTestLetter;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sergheimorari.letterprocessor.model.Letter;
import org.sergheimorari.letterprocessor.repository.LetterRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class LetterProcessorServiceTest {

  @Mock private LetterRepository letterRepository;
  @InjectMocks private LetterProcessorService service;

  @Test
  void getLettersByEmail_shouldReturnResultsPaged() {
    // given
    var email = "test@test.com";

    List<Letter> savedLetters = List.of(createTestLetter(createTestAddress()));
    Page<Letter> savedPage = new PageImpl<>(savedLetters);
    Pageable pageable = PageRequest.of(0, 10);

    when(letterRepository.findLettersByEmail(email, pageable)).thenReturn(savedPage);

    // when
    Page<Letter> result = service.getLettersByEmail(email, pageable);

    // then
    assertThat(result.getTotalElements()).isOne();
  }

  @Test
  void getLettersByEmail_NoLettersPresent_shouldReturnEmptyPage() {
    // given
    var email = "test@test.com";

    List<Letter> savedLetters = new ArrayList<>();
    Page<Letter> savedPage = new PageImpl<>(savedLetters);
    Pageable pageable = PageRequest.of(0, 10);

    when(letterRepository.findLettersByEmail(email, pageable)).thenReturn(savedPage);

    // when
    Page<Letter> result = service.getLettersByEmail(email, pageable);

    // then
    assertThat(result.getTotalElements()).isZero();
  }

  @Test
  void getAllLetters_Paginated() {
    // given
    var testLetter1 = createTestLetter(createTestAddress());
    var testLetter2 = createTestLetter(createTestAddress());
    var testLetter3 = createTestLetter(createTestAddress());
    List<Letter> savedLetters = List.of(testLetter1, testLetter2, testLetter3);

    Page<Letter> savedPage = new PageImpl<>(savedLetters);
    Pageable pageable = PageRequest.of(0, 10);
    when(letterRepository.findAll(pageable)).thenReturn(savedPage);

    // when
    Page<Letter> result = service.getAllLetters(pageable);

    // then
    assertThat(result.getTotalElements()).isEqualTo(3);
  }
}
