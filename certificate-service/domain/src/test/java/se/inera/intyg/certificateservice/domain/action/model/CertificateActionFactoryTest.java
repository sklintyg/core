package se.inera.intyg.certificateservice.domain.action.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;

class CertificateActionFactoryTest {

  @Test
  void shallReturnCertificateActionCreateIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.CREATE)
        .build();

    final var certificateAction = CertificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionCreate.class);
  }
}
