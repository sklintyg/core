package se.inera.intyg.certificateservice.domain.certificate.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PlaceholderCertificateTest {

  @Nested
  class RevokedTests {

    @Test
    void shouldThrowIfStatusIsNotSigned() {
      final var placeholderCertificate = PlaceholderCertificate.builder()
          .status(Status.REVOKED)
          .build();

      final var illegalStateException = assertThrows(IllegalStateException.class,
          () -> placeholderCertificate.revoke(null, null));

      assertEquals("Incorrect status '%s' - required status is '%s' or '%s' to revoke".formatted(
              Status.REVOKED, Status.SIGNED, Status.LOCKED_DRAFT),
          illegalStateException.getMessage());
    }

    @Test
    void shouldSetStatusToRevoked() {
      final var placeholderCertificate = PlaceholderCertificate.builder()
          .status(Status.SIGNED)
          .build();

      placeholderCertificate.revoke(null, null);
      assertEquals(Status.REVOKED, placeholderCertificate.status());
    }
  }
}