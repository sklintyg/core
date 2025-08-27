package se.inera.intyg.certificateservice.domain.action.certificate.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataAction.actionEvaluationBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.ALFA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_MEDICINCENTRUM;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_ALLERGIMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.AJLA_DOKTOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.alvaVardadministratorBuilder;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.common.model.AccessScope;
import se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate;

@ExtendWith(MockitoExtension.class)
class CertificateActionQuestionsNotAvailableTest {

  private CertificateActionQuestionsNotAvailable certificateActionQuestionsNotAvailable;
  private ActionEvaluation.ActionEvaluationBuilder actionEvaluationBuilder;
  private MedicalCertificate.MedicalCertificateBuilder certificateBuilder;
  private static final CertificateActionSpecification CERTIFICATE_ACTION_SPECIFICATION =
      CertificateActionSpecification.builder()
          .certificateActionType(CertificateActionType.QUESTIONS_NOT_AVAILABLE)
          .build();

  @InjectMocks
  CertificateActionFactory certificateActionFactory;

  @BeforeEach
  void setUp() {
    certificateActionQuestionsNotAvailable = (CertificateActionQuestionsNotAvailable) certificateActionFactory.create(
        CERTIFICATE_ACTION_SPECIFICATION
    );

    certificateBuilder = MedicalCertificate.builder()
        .status(Status.SIGNED)
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
  void shallReturnName() {
    assertEquals("Ärendekommunikation",
        certificateActionQuestionsNotAvailable.getName(Optional.empty()));
  }

  @Test
  void shallReturnType() {
    assertEquals(CertificateActionType.QUESTIONS_NOT_AVAILABLE,
        certificateActionQuestionsNotAvailable.getType());
  }

  @Test
  void shallReturnDescription() {
    assertEquals("Hantera kompletteringsbegäran, frågor och svar",
        certificateActionQuestionsNotAvailable.getDescription(Optional.empty()));
  }

  @Test
  void shallReturnFalseIfSent() {
    final var certificate = certificateBuilder
        .sent(TestDataCertificate.SENT)
        .build();
    final var actionEvaluation = actionEvaluationBuilder.build();

    assertFalse(certificateActionQuestionsNotAvailable.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected false when passing %s and %s".formatted(certificate, actionEvaluation)
    );
  }

  @Test
  void shallReturnTrueIfNotSent() {
    final var actionEvaluation = actionEvaluationBuilder().build();

    final var certificate = certificateBuilder
        .sent(null)
        .build();

    assertTrue(
        certificateActionQuestionsNotAvailable.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnTrueIfSigned() {
    final var actionEvaluation = actionEvaluationBuilder
        .user(alvaVardadministratorBuilder()
            .accessScope(AccessScope.ALL_CARE_PROVIDERS)
            .build())
        .build();

    final var certificate = certificateBuilder
        .status(Status.SIGNED)
        .build();

    assertTrue(
        certificateActionQuestionsNotAvailable.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnFalseIfNotSigned() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    final var certificate = certificateBuilder
        .status(Status.DRAFT)
        .build();

    assertFalse(
        certificateActionQuestionsNotAvailable.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnEmptyListIfEvaluateReturnsTrue() {
    final var certificate = certificateBuilder.build();

    final var actionEvaluation = actionEvaluationBuilder.build();

    final var actualResult = certificateActionQuestionsNotAvailable.reasonNotAllowed(
        Optional.of(certificate),
        Optional.of(actionEvaluation));

    assertTrue(actualResult.isEmpty());
  }

  @Test
  void shallReturnEnabledTrue() {
    final var certificate = certificateBuilder
        .status(Status.SIGNED)
        .build();

    final var actionEvaluation = actionEvaluationBuilder.build();

    final var enabled = certificateActionQuestionsNotAvailable.isEnabled(Optional.of(certificate),
        Optional.of(actionEvaluation));

    assertTrue(enabled);
  }
}