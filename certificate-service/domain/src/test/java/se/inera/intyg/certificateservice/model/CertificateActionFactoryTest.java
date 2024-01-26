package se.inera.intyg.certificateservice.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

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
