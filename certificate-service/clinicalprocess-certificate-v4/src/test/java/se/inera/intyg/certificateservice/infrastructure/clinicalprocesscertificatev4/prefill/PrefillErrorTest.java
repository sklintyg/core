package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
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

  @Test
  void shouldReturnInvalidSubAnswerId() {
    final var expectedId = "expectedId";
    final var actualIds = List.of("actualId1", "actualId2");
    final var answerId = "answerId";
    var error = PrefillError.invalidSubAnswerId(
        expectedId,
        actualIds,
        answerId
    );

    assertAll(
        () -> assertEquals(PrefillErrorType.INVALID_SUB_ANSWER_ID, error.type()),
        () -> assertEquals(
            "Invalid sub answer id. Expected '%s' but got '%s' for answer id %s".formatted(
                expectedId, actualIds, answerId), error.details())
    );
  }
}