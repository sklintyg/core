package se.inera.intyg.certificateservice.domain.action.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.ALFA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_MEDICINCENTRUM;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_ALLERGIMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.AJLA_DOKTOR;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate.CertificateBuilder;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.certificate.model.Sent;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;

class CertificateActionSendAfterSignTest {

  private CertificateActionSendAfterSign certificateActionSign;
  private ActionEvaluation.ActionEvaluationBuilder actionEvaluationBuilder;
  private CertificateBuilder certificateBuilder;
  private static final CertificateActionSpecification CERTIFICATE_ACTION_SPECIFICATION =
      CertificateActionSpecification.builder()
          .certificateActionType(CertificateActionType.SEND_AFTER_SIGN)
          .build();

  @BeforeEach
  void setUp() {
    certificateActionSign = (CertificateActionSendAfterSign) CertificateActionFactory.create(
        CERTIFICATE_ACTION_SPECIFICATION
    );

    certificateBuilder = Certificate.builder()
        .certificateMetaData(
            CertificateMetaData.builder()
                .issuingUnit(ALFA_ALLERGIMOTTAGNINGEN)
                .careUnit(ALFA_MEDICINCENTRUM)
                .careProvider(ALFA_REGIONEN)
                .patient(ATHENA_REACT_ANDERSSON)
                .build()
        );

    actionEvaluationBuilder = ActionEvaluation.builder()
        .user(AJLA_DOKTOR)
        .subUnit(ALFA_ALLERGIMOTTAGNINGEN)
        .patient(ATHENA_REACT_ANDERSSON)
        .careProvider(ALFA_REGIONEN)
        .careUnit(ALFA_MEDICINCENTRUM);
  }

  @Test
  void shallReturnTypeFromSpecification() {
    assertEquals(CertificateActionType.SEND_AFTER_SIGN, certificateActionSign.getType());
  }

  @Test
  void shallReturnTrueIfCertificateIsSigned() {
    final var certificate = certificateBuilder.status(Status.SIGNED).build();
    final var actionEvaluation = actionEvaluationBuilder.build();
    assertTrue(
        certificateActionSign.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)));
  }

  @Test
  void shallReturnFalseIfCertificateIsNotSigned() {
    final var certificate = certificateBuilder.status(Status.DRAFT).build();
    final var actionEvaluation = actionEvaluationBuilder.build();
    assertFalse(
        certificateActionSign.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)));
  }

  @Test
  void shallReturnFalseIfCertificateAlreadyIsSent() {
    final var certificate = certificateBuilder.sent(Sent.builder().build()).build();
    final var actionEvaluation = actionEvaluationBuilder.build();
    assertFalse(
        certificateActionSign.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)));
  }
}
