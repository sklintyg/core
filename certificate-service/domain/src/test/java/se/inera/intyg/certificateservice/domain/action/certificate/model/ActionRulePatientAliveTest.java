package se.inera.intyg.certificateservice.domain.action.certificate.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModel.FK7210_CERTIFICATE_MODEL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATLAS_REACT_ABRAHAMSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.AJLA_DOKTOR;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;

class ActionRulePatientAliveTest {

  private ActionRulePatientAlive actionRulePatientAlive;

  @BeforeEach
  void setUp() {
    actionRulePatientAlive = new ActionRulePatientAlive();
  }

  @Nested
  class PatientInActionEvaluation {

    @Test
    void shallReturnTrueIfPatientIsNotDeceased() {
      final var actionEvaluation = ActionEvaluation.builder()
          .patient(ATHENA_REACT_ANDERSSON)
          .user(AJLA_DOKTOR)
          .build();

      final var actualResult = actionRulePatientAlive.evaluate(Optional.empty(),
          Optional.of(actionEvaluation));

      assertTrue(actualResult);
    }

    @Test
    void shallReturnFalseIfPatientIsDeceased() {
      final var actionEvaluation = ActionEvaluation.builder()
          .patient(ATLAS_REACT_ABRAHAMSSON)
          .user(AJLA_DOKTOR)
          .build();

      final var actualResult = actionRulePatientAlive.evaluate(Optional.empty(),
          Optional.of(actionEvaluation));

      assertFalse(actualResult);
    }
  }

  @Nested
  class PatientInCertificate {

    @Test
    void shallReturnTrueIfPatientIsNotDeceased() {
      final var actionEvaluation = ActionEvaluation.builder()
          .user(AJLA_DOKTOR)
          .build();

      final var certificate = MedicalCertificate.builder()
          .certificateMetaData(
              CertificateMetaData.builder()
                  .patient(ATHENA_REACT_ANDERSSON)
                  .build()
          )
          .build();
      final var actualResult = actionRulePatientAlive.evaluate(Optional.of(certificate),
          Optional.of(actionEvaluation));

      assertTrue(actualResult);
    }

    @Test
    void shallReturnFalseIfPatientIsNotDeceased() {
      final var actionEvaluation = ActionEvaluation.builder()
          .user(AJLA_DOKTOR)
          .build();

      final var certificate = MedicalCertificate.builder()
          .certificateMetaData(
              CertificateMetaData.builder()
                  .patient(ATHENA_REACT_ANDERSSON)
                  .build()
          )
          .build();
      final var actualResult = actionRulePatientAlive.evaluate(Optional.of(certificate),
          Optional.of(actionEvaluation));

      assertTrue(actualResult);
    }
  }


  @Test
  void shallReturnTrueIfNoPatientInActionEvaluationOrCertificate() {
    final var actionEvaluation = ActionEvaluation.builder()
        .user(AJLA_DOKTOR)
        .build();

    final var certificate = MedicalCertificate.builder()
        .certificateMetaData(CertificateMetaData.builder()
            .build())
        .build();

    final var actualResult = actionRulePatientAlive.evaluate(Optional.of(certificate),
        Optional.of(actionEvaluation));

    assertTrue(actualResult);
  }

  @Test
  void shallReturnErrorMessage() {
    assertEquals(
        "Du saknar behörighet för den begärda åtgärden."
            + " Det krävs särskilda rättigheter eller en specifik befattning"
            + " för att hantera avlidna patienter.",
        actionRulePatientAlive.getReasonForPermissionDenied(Optional.empty())
    );
  }

  @Test
  void shallReturnErrorMessageWithCertificate() {

    final var certificate = MedicalCertificate.builder()
        .certificateMetaData(CertificateMetaData.builder()
            .patient(ATLAS_REACT_ABRAHAMSSON)
            .build())
        .certificateModel(FK7210_CERTIFICATE_MODEL)
        .build();

    assertEquals(
        String.format("Cannot issue intyg type %s for deceased patient %s",
            certificate.certificateModel().type().displayName(),
            certificate.certificateMetaData().patient().id().idWithDash()),
        actionRulePatientAlive.getReasonForPermissionDenied(Optional.of(certificate))
    );
  }
}