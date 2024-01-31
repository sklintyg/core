package se.inera.intyg.certificateservice.domain.action.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.Blocked;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.patient.model.Deceased;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;
import se.inera.intyg.certificateservice.domain.user.model.User;

class CertificateActionCreateTest {

  private CertificateActionCreate certificateActionCreate;

  private static final CertificateActionSpecification CERTIFICATE_ACTION_SPECIFICATION =
      CertificateActionSpecification.builder()
          .certificateActionType(CertificateActionType.CREATE)
          .build();

  @BeforeEach
  void setUp() {
    certificateActionCreate = new CertificateActionCreate(CERTIFICATE_ACTION_SPECIFICATION);
  }

  @Test
  void shallReturnName() {
    assertEquals("Skapa intyg", certificateActionCreate.getName());
  }

  @Test
  void shallReturnType() {
    assertEquals(CertificateActionType.CREATE, certificateActionCreate.getType());
  }

  @Test
  void shallReturnDescription() {
    assertEquals("Skapa ett intygsutkast", certificateActionCreate.getDescription());
  }

  @Test
  void shallReturnFalseIfPatientIsDeceased() {
    final var actionEvaluation = ActionEvaluation.builder()
        .patient(
            Patient.builder()
                .deceased(new Deceased(true))
                .build()
        )
        .user(
            User.builder()
                .blocked(new Blocked(false))
                .build()
        )
        .build();

    final var actualResult = certificateActionCreate.evaluate(actionEvaluation);

    assertFalse(actualResult);
  }

  @Test
  void shallReturnTrueIfPatientIsNotDeceased() {
    final var actionEvaluation = ActionEvaluation.builder()
        .patient(
            Patient.builder()
                .deceased(new Deceased(false))
                .build()
        )
        .user(
            User.builder()
                .blocked(new Blocked(false))
                .build()
        )
        .build();

    final var actualResult = certificateActionCreate.evaluate(actionEvaluation);

    assertTrue(actualResult);
  }

  @Test
  void shallReturnFalseIfUserIsBlocked() {
    final var actionEvaluation = ActionEvaluation.builder()
        .patient(
            Patient.builder()
                .deceased(new Deceased(false))
                .build()
        )
        .user(
            User.builder()
                .blocked(new Blocked(true))
                .build()
        )
        .build();

    final var actualResult = certificateActionCreate.evaluate(actionEvaluation);

    assertFalse(actualResult);
  }

  @Test
  void shallReturnTrueIfUserIsNotBlocked() {
    final var actionEvaluation = ActionEvaluation.builder()
        .patient(
            Patient.builder()
                .deceased(new Deceased(false))
                .build()
        )
        .user(
            User.builder()
                .blocked(new Blocked(false))
                .build()
        )
        .build();

    final var actualResult = certificateActionCreate.evaluate(actionEvaluation);

    assertTrue(actualResult);
  }
}