package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;

class ElementMessageTest {

  @Test
  void shallReturnTrueIfIncludedStatusesContainsStatus() {
    final var elementMessage = ElementMessage.builder()
        .includedForStatuses(List.of(Status.DRAFT))
        .build();

    final var certificate = MedicalCertificate.builder()
        .status(Status.DRAFT)
        .build();

    assertTrue(elementMessage.include(certificate));
  }

  @Test
  void shallReturnFalseIfIncludedStatusesDontContainStatus() {
    final var elementMessage = ElementMessage.builder()
        .includedForStatuses(List.of(Status.DRAFT))
        .build();

    final var certificate = MedicalCertificate.builder()
        .status(Status.SIGNED)
        .build();

    assertFalse(elementMessage.include(certificate));
  }
}