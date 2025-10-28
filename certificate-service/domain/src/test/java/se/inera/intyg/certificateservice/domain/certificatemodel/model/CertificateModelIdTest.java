package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CertificateModelIdTest {

  @Nested
  class MatchesTests {

    @Test
    void shouldReturnTrueIfTypeAndVersionMatches() {
      final var certificateModelId = CertificateModelId.builder()
          .type(new CertificateType("type"))
          .version(new CertificateVersion("version"))
          .build();

      assertTrue(certificateModelId.matches("type", List.of("version")));
    }

    @Test
    void shouldReturnFalseIfTypeMatchesButVersionDoesNot() {
      final var certificateModelId = CertificateModelId.builder()
          .type(new CertificateType("type"))
          .version(new CertificateVersion("version"))
          .build();

      assertFalse(certificateModelId.matches("type", List.of("anotherVersion")));
    }

    @Test
    void shouldReturnFalseIfTypeDoesNotMatchButVersionDoes() {
      final var certificateModelId = CertificateModelId.builder()
          .type(new CertificateType("type"))
          .version(new CertificateVersion("version"))
          .build();

      assertFalse(certificateModelId.matches("anotherType", List.of("version")));
    }
  }

  @Nested
  class IsLatestActiveVersionTests {

    @Test
    void shouldReturnTrueIfVersionMatches() {
      final var certificateModelId = CertificateModelId.builder()
          .type(new CertificateType("type"))
          .version(new CertificateVersion("1.0"))
          .build();

      assertTrue(certificateModelId.version().isLastestActiveVersion(
          List.of(new CertificateVersion("1.0"))
      ));
    }

    @Test
    void shouldReturnFalseIfVersionDoesNotMatch() {
      final var certificateModelId = CertificateModelId.builder()
          .type(new CertificateType("type"))
          .version(new CertificateVersion("1.0"))
          .build();

      assertFalse(certificateModelId.version().isLastestActiveVersion(
          List.of(new CertificateVersion("2.0"))
      ));
    }
  }
}