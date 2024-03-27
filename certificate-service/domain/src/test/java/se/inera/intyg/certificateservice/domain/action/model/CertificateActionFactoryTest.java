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

  @Test
  void shallReturnCertificateActionReadIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.READ)
        .build();

    final var certificateAction = CertificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionRead.class);
  }

  @Test
  void shallReturnCertificateActionUpdateIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.UPDATE)
        .build();

    final var certificateAction = CertificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionUpdate.class);
  }

  @Test
  void shallReturnCertificateActionDeleteIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.DELETE)
        .build();

    final var certificateAction = CertificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionDelete.class);
  }

  @Test
  void shallReturnCertificateActionSignIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.SIGN)
        .build();

    final var certificateAction = CertificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionSign.class);
  }

  @Test
  void shallReturnCertificateActionPrintIfExistInSpecification() {
    final var certificateActionSpecification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.PRINT)
        .build();

    final var certificateAction = CertificateActionFactory.create(certificateActionSpecification);

    assert certificateAction != null;
    assertEquals(certificateAction.getClass(), CertificateActionPrint.class);
  }
}
