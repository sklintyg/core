package se.inera.intyg.certificateservice.domain.action.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ANONYMA_REACT_ATTILA;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATLAS_REACT_ABRAHAMSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.AJLA_DOKTOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.ALVA_VARDADMINISTRATOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.ajlaDoctorBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.BLOCKED_TRUE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;

class CertificateActionCreateTest {

  private CertificateActionCreate certificateActionCreate;

  private static final CertificateActionSpecification CERTIFICATE_ACTION_SPECIFICATION =
      CertificateActionSpecification.builder()
          .certificateActionType(CertificateActionType.CREATE)
          .build();

  @BeforeEach
  void setUp() {
    certificateActionCreate = (CertificateActionCreate) CertificateActionFactory.create(
        CERTIFICATE_ACTION_SPECIFICATION
    );
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
    assertEquals("Skapa ett intygsutkast.", certificateActionCreate.getDescription());
  }

  @Test
  void shallReturnFalseIfPatientIsDeceased() {
    final var actionEvaluation = ActionEvaluation.builder()
        .patient(ATLAS_REACT_ABRAHAMSSON)
        .user(AJLA_DOKTOR)
        .build();

    final var actualResult = certificateActionCreate.evaluate(actionEvaluation);

    assertFalse(actualResult);
  }

  @Test
  void shallReturnTrueIfPatientIsNotDeceased() {
    final var actionEvaluation = ActionEvaluation.builder()
        .patient(ATHENA_REACT_ANDERSSON)
        .user(AJLA_DOKTOR)
        .build();

    final var actualResult = certificateActionCreate.evaluate(actionEvaluation);

    assertTrue(actualResult);
  }

  @Test
  void shallReturnFalseIfUserIsBlocked() {
    final var actionEvaluation = ActionEvaluation.builder()
        .patient(ATHENA_REACT_ANDERSSON)
        .user(
            ajlaDoctorBuilder()
                .blocked(BLOCKED_TRUE)
                .build()
        )
        .build();

    final var actualResult = certificateActionCreate.evaluate(actionEvaluation);

    assertFalse(actualResult);
  }

  @Test
  void shallReturnTrueIfUserIsNotBlocked() {
    final var actionEvaluation = ActionEvaluation.builder()
        .patient(ATHENA_REACT_ANDERSSON)
        .user(AJLA_DOKTOR)
        .build();

    final var actualResult = certificateActionCreate.evaluate(actionEvaluation);

    assertTrue(actualResult);
  }

  @Test
  void shallReturnFalseIfUserIsCareAdminAndPatientIsProtectedPerson() {
    final var actionEvaluation = ActionEvaluation.builder()
        .patient(ANONYMA_REACT_ATTILA)
        .user(ALVA_VARDADMINISTRATOR)
        .build();

    final var actualResult = certificateActionCreate.evaluate(actionEvaluation);

    assertFalse(actualResult);
  }

  @Test
  void shallReturnTrueIfUserIsDoctorAndPatientIsProtectedPerson() {
    final var actionEvaluation = ActionEvaluation.builder()
        .patient(ANONYMA_REACT_ATTILA)
        .user(AJLA_DOKTOR)
        .build();

    final var actualResult = certificateActionCreate.evaluate(actionEvaluation);

    assertTrue(actualResult);
  }

  @Test
  void shallReturnFalseIfPatientMissing() {
    final var actionEvaluation = ActionEvaluation.builder()
        .user(AJLA_DOKTOR)
        .build();

    final var actualResult = certificateActionCreate.evaluate(actionEvaluation);

    assertFalse(actualResult);
  }

  @Test
  void shallReturnFalseIfUserMissing() {
    final var actionEvaluation = ActionEvaluation.builder()
        .patient(ATHENA_REACT_ANDERSSON)
        .build();

    final var actualResult = certificateActionCreate.evaluate(actionEvaluation);

    assertFalse(actualResult);
  }

  @Test
  void shallReturnReasonNotAllowedIfEvaluateReturnsFalse() {
    final var actionEvaluation = ActionEvaluation.builder()
        .patient(ANONYMA_REACT_ATTILA)
        .user(ALVA_VARDADMINISTRATOR)
        .build();

    final var actualResult = certificateActionCreate.reasonNotAllowed(actionEvaluation);

    assertFalse(actualResult.isEmpty());
  }

  @Test
  void shallReturnEmptyListIfEvaluateReturnsTrue() {
    final var actionEvaluation = ActionEvaluation.builder()
        .patient(ANONYMA_REACT_ATTILA)
        .user(AJLA_DOKTOR)
        .build();

    final var actualResult = certificateActionCreate.reasonNotAllowed(actionEvaluation);

    assertTrue(actualResult.isEmpty());
  }
}
