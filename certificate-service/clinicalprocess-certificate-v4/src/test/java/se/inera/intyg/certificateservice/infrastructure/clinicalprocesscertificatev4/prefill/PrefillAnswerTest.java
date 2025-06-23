package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PrefillAnswerTest {

  @Test
  void shouldReturnAnswerNotFoundError() {
    final var answer = PrefillAnswer.answerNotFound("MI1");
    assertAll(
        () -> assertEquals(1, answer.getErrors().size()),
        () -> assertEquals(PrefillErrorType.ANSWER_NOT_FOUND, answer.getErrors().getFirst().type()),
        () -> assertEquals("Answer with id MI1 not found in certificate model",
            answer.getErrors().getFirst().details())
    );
  }

  @Test
  void shouldReturnSubAnswerNotFoundError() {
    final var answer = PrefillAnswer.subAnswerNotFound("MI1", "MI1.1");
    assertAll(
        () -> assertEquals(1, answer.getErrors().size()),
        () -> assertEquals(PrefillErrorType.SUB_ANSWER_NOT_FOUND,
            answer.getErrors().getFirst().type()),
        () -> assertEquals("Sub-answer with id MI1.1 not found in answer with id MI1",
            answer.getErrors().getFirst().details())
    );
  }

  @Test
  void shouldReturnInvalidFormatError() {
    final var answer = PrefillAnswer.invalidFormat();
    assertAll(
        () -> assertEquals(1, answer.getErrors().size()),
        () -> assertEquals(PrefillErrorType.INVALID_FORMAT, answer.getErrors().getFirst().type()),
        () -> assertEquals("Invalid format", answer.getErrors().getFirst().details())
    );
  }
}