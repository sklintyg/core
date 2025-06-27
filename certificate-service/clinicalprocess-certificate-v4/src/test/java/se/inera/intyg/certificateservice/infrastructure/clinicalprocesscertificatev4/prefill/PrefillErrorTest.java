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
    var error = PrefillError.invalidFormat("id", "message");
    assertAll(
        () -> assertEquals(PrefillErrorType.INVALID_FORMAT, error.type()),
        () -> assertEquals("Invalid format for answer id 'id' - reason: message", error.details())
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