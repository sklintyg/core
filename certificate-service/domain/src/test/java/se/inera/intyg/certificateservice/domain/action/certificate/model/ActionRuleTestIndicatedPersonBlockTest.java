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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;
import se.inera.intyg.certificateservice.domain.patient.model.TestIndicated;

@ExtendWith(MockitoExtension.class)
class ActionRuleTestIndicatedPersonBlockTest {

  private ActionRuleBlockTestIndicatedPerson actionRuleBlockTestIndicatedPerson;

  @BeforeEach
  void setUp() {
    actionRuleBlockTestIndicatedPerson = new ActionRuleBlockTestIndicatedPerson();
  }

  @Test
  void shallReturnFalseIfPatientIsTestIndicated() {
    final var actionEvaluation = actionEvaluationBuilder()
        .patient(
            Patient.builder()
                .testIndicated(new TestIndicated(true))
                .build()
        )
        .build();

    assertFalse(
        actionRuleBlockTestIndicatedPerson.evaluate(Optional.empty(), Optional.of(actionEvaluation))
    );
  }

  @Test
  void shallReturnTrueIfPatientIsNotTestIndicated() {
    final var actionEvaluation = actionEvaluationBuilder()
        .patient(
            Patient.builder()
                .testIndicated(new TestIndicated(false))
                .build()
        )
        .user(ALVA_VARDADMINISTRATOR)
        .build();

    assertTrue(
        actionRuleBlockTestIndicatedPerson.evaluate(Optional.empty(),
            Optional.of(actionEvaluation)));
  }

  @Test
  void shallReturnTrueIfPatientOnCertificateIsNotTestIndicated() {
    final var actionEvaluation = actionEvaluationBuilder()
        .patient(ANONYMA_REACT_ATTILA)
        .build();

    final var certificate = fk7210CertificateBuilder()
        .certificateMetaData(
            CertificateMetaData.builder()
                .issuer(AJLA_DOKTOR)
                .patient(
                    Patient.builder()
                        .testIndicated(new TestIndicated(false))
                        .build()
                )
                .issuingUnit(ALFA_ALLERGIMOTTAGNINGEN)
                .careUnit(ALFA_MEDICINCENTRUM)
                .careProvider(ALFA_REGIONEN)
                .build()
        )
        .build();

    assertTrue(
        actionRuleBlockTestIndicatedPerson.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation))
    );
  }

  @Test
  void shallReturnFalseIfPatientOnCertificateIsTestIndicated() {
    final var actionEvaluation = actionEvaluationBuilder()
        .patient(ANONYMA_REACT_ATTILA)
        .user(ALVA_VARDADMINISTRATOR)
        .build();

    final var certificate = fk7210CertificateBuilder()
        .certificateMetaData(
            CertificateMetaData.builder()
                .issuer(AJLA_DOKTOR)
                .patient(
                    Patient.builder()
                        .testIndicated(new TestIndicated(true))
                        .build()
                )
                .issuingUnit(ALFA_ALLERGIMOTTAGNINGEN)
                .careUnit(ALFA_MEDICINCENTRUM)
                .careProvider(ALFA_REGIONEN)
                .build()
        )
        .build();

    assertFalse(
        actionRuleBlockTestIndicatedPerson.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation))
    );
  }

  @Test
  void shallReturnErrorMessage() {
    assertEquals(
        "Åtgärden kan inte utföras för testpersoner.",
        actionRuleBlockTestIndicatedPerson.getReasonForPermissionDenied()
    );
  }

}