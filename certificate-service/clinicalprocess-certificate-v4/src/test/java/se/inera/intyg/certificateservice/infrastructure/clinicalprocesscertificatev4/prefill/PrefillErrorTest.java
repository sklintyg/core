package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PrefillErrorTest {

  @Test
  void shouldReturnAnswerNotFoundError() {
    var error = PrefillError.answerNotFound("MI1");
    assertAll(
        () -> assertEquals(PrefillErrorType.ANSWER_NOT_FOUND, error.type()),
        () -> assertEquals("Answer with id MI1 not found in certificate model", error.details())
    );
  }

  @Test
  void shouldReturnSubAnswerNotFoundError() {
    var error = PrefillError.subAnswerNotFound("MI1", "MI1.1");
    assertAll(
        () -> assertEquals(PrefillErrorType.SUB_ANSWER_NOT_FOUND, error.type()),
        () -> assertEquals("Sub-answer with id MI1.1 not found in answer with id MI1",
            error.details())
    );
  }

  @Test
  void shouldReturnWrongNumberOfAnswersError() {
    var error = PrefillError.wrongNumberOfAnswers(1, 2);
    assertAll(
        () -> assertEquals(PrefillErrorType.WRONG_NUMBER_OF_ANSWERS, error.type()),
        () -> assertEquals("Expected 1 answers but got 2", error.details())
    );
  }

  @Test
  void shouldReturnTechnicalError() {
    var error = PrefillError.technicalError("investigation failed");
    assertAll(
        () -> assertEquals(PrefillErrorType.TECHNICAL_ERROR, error.type()),
        () -> assertEquals("A technical error occurred investigation failed", error.details())
    );
  }

  @Test
  void shouldReturnWrongConfigurationTypeError() {
    var error = PrefillError.wrongConfigurationType();
    assertAll(
        () -> assertEquals(PrefillErrorType.TECHNICAL_ERROR, error.type()),
        () -> assertEquals("A technical error occurred Wrong configuration type", error.details())
    );
  }

  @Test
  void shouldReturnInvalidFormatError() {
    var error = PrefillError.invalidFormat();
    assertAll(
        () -> assertEquals(PrefillErrorType.INVALID_FORMAT, error.type()),
        () -> assertEquals("Invalid format", error.details())
    );
  }

  @Test
  void shouldReturnSubAnswersNotFoundError() {
    var error = PrefillError.subAnswersNotFound("MI2");
    assertAll(
        () -> assertEquals(PrefillErrorType.SUB_ANSWER_NOT_FOUND, error.type()),
        () -> assertEquals("Sub-answer expected in answer with id MI2", error.details())
    );
  }

  @Test
  void shouldReturnUnmarshallingError() {
    var error = PrefillError.unmarshallingError("MI5", "MI5.1");
    assertAll(
        () -> assertEquals(PrefillErrorType.UNMARSHALL_ERROR, error.type()),
        () -> assertEquals(
            "Content of sub-answer with id MI5 in answer with id MI5.1 could not be unmarshalled",
            error.details())
    );
  }

  @Test
  void shouldReturnMissingConverterError() {
    var error = PrefillError.missingConverter("MedicalInvestigationConverter");
    assertAll(
        () -> assertEquals(PrefillErrorType.MISSING_CONVERTER, error.type()),
        () -> assertEquals("No converter found for MedicalInvestigationConverter", error.details())
    );
  }
}
