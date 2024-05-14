package se.inera.intyg.certificateservice.application.citizen.validation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdTypeDTO;

class CitizenCertificateRequestValidatorTest {

  private static final CitizenCertificateRequestValidator validator = new CitizenCertificateRequestValidator();
  private static final String CERTIFICATE_ID = "certificateId";
  private static final String PERSON_ID = "191212121212";

  @Nested
  class CertificateId {

    @Test
    void shouldThrowIfCertificateIdIsNull() {
      final var personId = PersonIdDTO.builder()
          .id(PERSON_ID)
          .build();

      assertThrows(IllegalArgumentException.class,
          () -> validator.validate(null, personId));
    }

    @Test
    void shouldThrowIfCertificateIdIsEmpty() {
      assertThrows(IllegalArgumentException.class, () -> validator.validate(""));
    }

    @Test
    void shouldThrowIfCertificateIdIsBlank() {
      assertThrows(IllegalArgumentException.class, () -> validator.validate("   "));
    }

    @Test
    void shouldNotThrowIfAllConditionsAreMet() {
      assertDoesNotThrow(() -> validator.validate(CERTIFICATE_ID));
    }
  }

  @Nested
  class PersonIdAndCertificateId {

    @Test
    void shouldThrowIfPersonIdIsNull() {
      assertThrows(IllegalArgumentException.class, () ->
          validator.validate(CERTIFICATE_ID, null));
    }

    @Test
    void shouldThrowIfPersonIdIdIsNull() {
      final var personId = PersonIdDTO.builder()
          .type(PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER)
          .build();

      assertThrows(IllegalArgumentException.class, () ->
          validator.validate(CERTIFICATE_ID, personId
          ));
    }

    @Test
    void shouldThrowIfPersonIdIdIsEmpty() {
      final var personId = PersonIdDTO.builder()
          .id("")
          .type(PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER)
          .build();

      assertThrows(IllegalArgumentException.class, () ->
          validator.validate(CERTIFICATE_ID, personId
          ));
    }

    @Test
    void shouldThrowIfPersonIdTypeIsNull() {
      final var personId = PersonIdDTO.builder()
          .id(PERSON_ID)
          .build();

      assertThrows(IllegalArgumentException.class, () ->
          validator.validate(CERTIFICATE_ID, personId
          ));
    }

    @Test
    void shouldNotThrowIfAllConditionsAreMet() {
      final var personId = PersonIdDTO.builder()
          .id(PERSON_ID)
          .type(PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER)
          .build();

      assertDoesNotThrow(() -> validator.validate(CERTIFICATE_ID, personId));
    }
  }

  @Nested
  class PersonId {

    @Test
    void shouldThrowIfPersonIdIdIsNull() {
      final var personId = PersonIdDTO.builder()
          .type(PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER)
          .build();

      assertThrows(IllegalArgumentException.class, () -> validator.validate(personId));
    }

    @Test
    void shouldThrowIfPersonIdIdIsEmpty() {
      final var personId = PersonIdDTO.builder()
          .id("")
          .type(PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER)
          .build();

      assertThrows(IllegalArgumentException.class, () -> validator.validate(personId));
    }

    @Test
    void shouldThrowIfPersonIdTypeIsNull() {
      final var personId = PersonIdDTO.builder()
          .id(PERSON_ID)
          .build();

      assertThrows(IllegalArgumentException.class, () -> validator.validate(personId));
    }

    @Test
    void shouldNotThrowIfAllConditionsAreMet() {
      final var personId = PersonIdDTO.builder()
          .id(PERSON_ID)
          .type(PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER)
          .build();

      assertDoesNotThrow(() -> validator.validate(personId));
    }
  }
}