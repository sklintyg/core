package se.inera.intyg.certificateservice.domain.action.certificate.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7804CertificateBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModel.fk7804certificateModelBuilder;

import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class ActionRuleInactiveCertificateModelTest {

  @Test
  void shouldReturnFalseIfCertificateIsActive() {
    final var activeCertificate = fk7804CertificateBuilder()
        .build();

    final var result = new ActionRuleInactiveCertificateModel()
        .evaluate(
            Optional.of(activeCertificate),
            Optional.empty()
        );

    assertFalse(result);
  }


  @Test
  void shouldReturnTrueIfCertificateIsInactive() {
    final var inactiveCertificate = fk7804CertificateBuilder()
        .certificateModel(
            fk7804certificateModelBuilder()
                .activeFrom(LocalDateTime.now().plusDays(1))
                .build()
        )
        .build();

    final var result = new ActionRuleInactiveCertificateModel()
        .evaluate(
            Optional.of(inactiveCertificate),
            Optional.empty()
        );

    assertTrue(result);
  }

  @Test
  void shouldReturnFalseIfCertificateIsMissing() {
    final var result = new ActionRuleInactiveCertificateModel()
        .evaluate(
            Optional.empty(),
            Optional.empty()
        );

    assertFalse(result);
  }
}