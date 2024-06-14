package se.inera.intyg.certificateservice.domain.action.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.ALFA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_MEDICINCENTRUM;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataRelation.RELATION_COMPLEMENT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_ALLERGIMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.AJLA_DOKTOR;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation.ActionEvaluationBuilder;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate.CertificateBuilder;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate;

class CertificateActionMessagesAdministrativeTest {

  private CertificateActionMessagesAdministrative certificateActionMessagesAdministrative;

  private static final CertificateActionSpecification CERTIFICATE_ACTION_SPECIFICATION =
      CertificateActionSpecification.builder()
          .certificateActionType(CertificateActionType.MESSAGES_ADMINISTRATIVE)
          .build();
  private CertificateBuilder certificateBuilder;
  private ActionEvaluationBuilder actionEvaluationBuilder;

  @BeforeEach
  void setUp() {
    certificateActionMessagesAdministrative = (CertificateActionMessagesAdministrative) CertificateActionFactory.create(
        CERTIFICATE_ACTION_SPECIFICATION
    );

    certificateBuilder = Certificate.builder()
        .status(Status.SIGNED)
        .sent(null)
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
    assertEquals("Administrativa frågor",
        certificateActionMessagesAdministrative.getName(Optional.empty()));
  }

  @Test
  void shallReturnType() {
    assertEquals(CertificateActionType.MESSAGES_ADMINISTRATIVE,
        certificateActionMessagesAdministrative.getType()
    );
  }

  @Test
  void shallReturnDescription() {
    assertEquals("Funktionen finns inte för detta intyg.",
        certificateActionMessagesAdministrative.getDescription(Optional.empty()));
  }

  @Test
  void shallReturnTrueIfSigned() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    final var certificate = certificateBuilder
        .status(Status.SIGNED)
        .build();

    assertTrue(
        certificateActionMessagesAdministrative.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnFalseIfDeletedDraft() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    final var certificate = certificateBuilder
        .status(Status.DELETED_DRAFT)
        .build();

    assertFalse(
        certificateActionMessagesAdministrative.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnFalseIfDraft() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    final var certificate = certificateBuilder
        .status(Status.DRAFT)
        .build();

    assertFalse(
        certificateActionMessagesAdministrative.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnTrueIfDraftButIsComplementingCertificate() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    final var certificate = certificateBuilder
        .status(Status.DRAFT)
        .parent(
            RELATION_COMPLEMENT
        )
        .build();

    assertTrue(
        certificateActionMessagesAdministrative.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnFalseIfRevoked() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    final var certificate = certificateBuilder
        .status(Status.REVOKED)
        .build();

    assertTrue(
        certificateActionMessagesAdministrative.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnTrueIfNotSent() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    final var certificate = certificateBuilder
        .status(Status.SIGNED)
        .build();

    assertTrue(
        certificateActionMessagesAdministrative.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnTrueIfSent() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    final var certificate = certificateBuilder
        .status(Status.SIGNED)
        .sent(TestDataCertificate.SENT)
        .build();

    assertTrue(
        certificateActionMessagesAdministrative.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnReasonNotAllowedIfEvaluateReturnsFalse() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    final var certificate = certificateBuilder
        .status(Status.DRAFT)
        .build();

    final var actualResult = certificateActionMessagesAdministrative.reasonNotAllowed(
        Optional.of(certificate), Optional.of(actionEvaluation)
    );

    assertFalse(actualResult.isEmpty());
  }

  @Test
  void shallReturnEmptyListIfEvaluateReturnsTrue() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    final var certificate = certificateBuilder
        .status(Status.SIGNED)
        .build();

    final var actualResult = certificateActionMessagesAdministrative.reasonNotAllowed(
        Optional.of(certificate),
        Optional.of(actionEvaluation));

    assertTrue(actualResult.isEmpty());
  }

  @Test
  void shallReturnEnabledFalse() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    final var certificate = certificateBuilder
        .status(Status.SIGNED)
        .build();

    final var enabled = certificateActionMessagesAdministrative.isEnabled(Optional.of(certificate),
        Optional.of(actionEvaluation));

    assertFalse(enabled);
  }

  @Test
  void shallReturnEnabledTrue() {
    final var specification =
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.MESSAGES_ADMINISTRATIVE)
            .isEnabled(true)
            .build();

    final var actionMessagesAdministrative = (CertificateActionMessagesAdministrative) CertificateActionFactory.create(
        specification
    );

    final var actionEvaluation = actionEvaluationBuilder.build();

    final var certificate = certificateBuilder
        .status(Status.SIGNED)
        .build();

    final var enabled = actionMessagesAdministrative.isEnabled(Optional.of(certificate),
        Optional.of(actionEvaluation));

    assertTrue(enabled);
  }
}
