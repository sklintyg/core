package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;

class TestabilityEnabledInMemoryCertificateRepositoryTest {

  private TestabilityEnabledInMemoryCertificateRepository repository;

  @BeforeEach
  void setUp() {
    repository = new TestabilityEnabledInMemoryCertificateRepository();
  }

  @Test
  void shallRemoveAnyCertificatesWhenCallingReset() {
    final var certificateId = new CertificateId("certificateId");
    repository.insert(
        Certificate.builder()
            .id(certificateId)
            .build()
    );
    repository.remove(List.of(certificateId));
    assertFalse(repository.exists(certificateId),
        () -> "Expected certificateId '%s' to been removed".formatted(certificateId)
    );
  }
}