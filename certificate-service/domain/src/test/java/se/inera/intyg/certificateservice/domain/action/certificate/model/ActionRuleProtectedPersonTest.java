package se.inera.intyg.certificateservice.domain.action.certificate.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataAction.actionEvaluationBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.ALFA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_MEDICINCENTRUM;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7210CertificateBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ANONYMA_REACT_ATTILA;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.AJLA_DOKTOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_ALLERGIMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.ALVA_VARDADMINISTRATOR;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionRuleProtectedPerson;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;

class ActionRuleProtectedPersonTest {

  private ActionRuleProtectedPerson actionRuleProtectedPerson;

  @BeforeEach
  void setUp() {
    actionRuleProtectedPerson = new ActionRuleProtectedPerson();
  }

  @Test
  void shallReturnTrueIfPatientIsProtectedAndUserIsDoctor() {
    final var actionEvaluation = actionEvaluationBuilder()
        .patient(ANONYMA_REACT_ATTILA)
        .build();

    assertTrue(
        actionRuleProtectedPerson.evaluate(Optional.empty(), Optional.of(actionEvaluation))
    );
  }

  @Test
  void shallReturnFalseIfPatientIsProtectedAndUserIsDoctor() {
    final var actionEvaluation = actionEvaluationBuilder()
        .patient(ANONYMA_REACT_ATTILA)
        .user(ALVA_VARDADMINISTRATOR)
        .build();

    assertFalse(
        actionRuleProtectedPerson.evaluate(Optional.empty(), Optional.of(actionEvaluation)));
  }

  @Test
  void shallReturnTrueIfPatientOnCertificateIsProtectedAndUserIsDoctor() {
    final var actionEvaluation = actionEvaluationBuilder()
        .patient(ANONYMA_REACT_ATTILA)
        .build();

    final var certificate = fk7210CertificateBuilder()
        .certificateMetaData(
            CertificateMetaData.builder()
                .issuer(AJLA_DOKTOR)
                .patient(ANONYMA_REACT_ATTILA)
                .issuingUnit(ALFA_ALLERGIMOTTAGNINGEN)
                .careUnit(ALFA_MEDICINCENTRUM)
                .careProvider(ALFA_REGIONEN)
                .build()
        )
        .build();

    assertTrue(
        actionRuleProtectedPerson.evaluate(Optional.of(certificate), Optional.of(actionEvaluation))
    );
  }

  @Test
  void shallReturnFalseIfPatientOnCertificateIsProtectedAndUserIsDoctor() {
    final var actionEvaluation = actionEvaluationBuilder()
        .patient(ANONYMA_REACT_ATTILA)
        .user(ALVA_VARDADMINISTRATOR)
        .build();

    final var certificate = fk7210CertificateBuilder()
        .certificateMetaData(
            CertificateMetaData.builder()
                .issuer(AJLA_DOKTOR)
                .patient(ANONYMA_REACT_ATTILA)
                .issuingUnit(ALFA_ALLERGIMOTTAGNINGEN)
                .careUnit(ALFA_MEDICINCENTRUM)
                .careProvider(ALFA_REGIONEN)
                .build()
        )
        .build();

    assertFalse(
        actionRuleProtectedPerson.evaluate(Optional.of(certificate), Optional.of(actionEvaluation))
    );
  }

  @Test
  void shallReturnErrorMessage() {
    assertEquals(
        "Du saknar behörighet för den begärda åtgärden."
            + " Det krävs särskilda rättigheter eller en specifik befattning"
            + " för att hantera patienter med skyddade personuppgifter.",
        actionRuleProtectedPerson.getReasonForPermissionDenied()
    );
  }
}
