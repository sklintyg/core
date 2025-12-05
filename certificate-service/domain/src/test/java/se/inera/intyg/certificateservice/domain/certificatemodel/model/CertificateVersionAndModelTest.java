package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CertificateVersionAndModelTest {

  @Nested
  class IsActiveTests {

    @Test
    void shouldReturnFalseIfModelIsNull() {
      final var certificateVersion = new CertificateVersionAndModel("version", null);
      assertFalse(certificateVersion.isActive());
    }

    @Test
    void shouldReturnFalseIfNotActive() {
      final var certificateModel = CertificateModel.builder()
          .activeFrom(LocalDateTime.now().plusDays(1))
          .build();

      final var certificateVersion = new CertificateVersionAndModel("version", certificateModel);
      assertFalse(certificateVersion.isActive());
    }

    @Test
    void shouldReturnTrueIfActive() {
      final var certificateModel = CertificateModel.builder()
          .activeFrom(LocalDateTime.now().minusDays(1))
          .build();

      final var certificateVersion = new CertificateVersionAndModel("version", certificateModel);
      assertTrue(certificateVersion.isActive());
    }
  }
}