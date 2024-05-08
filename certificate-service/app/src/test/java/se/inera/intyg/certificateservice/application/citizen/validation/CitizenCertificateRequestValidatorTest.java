package se.inera.intyg.certificateservice.application.citizen.validation;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class CitizenCertificateRequestValidatorTest {

  private static final CitizenCertificateRequestValidator validator = new CitizenCertificateRequestValidator();

  @Test
  void shouldThrowIfCertificateIdIsNull() {
    assertThrows(IllegalArgumentException.class, () -> validator.validate(null));
  }

  @Test
  void shouldThrowIfCertificateIdIsEmpty() {
    assertThrows(IllegalArgumentException.class, () -> validator.validate(""));
  }

  @Test
  void shouldThrowIfCertificateIdIsBlank() {
    assertThrows(IllegalArgumentException.class, () -> validator.validate("   "));
  }
}