package se.inera.intyg.certificateservice.domain.action.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.ALFA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.BETA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_MEDICINCENTRUM;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.BETA_VARDCENTRAL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ANONYMA_REACT_ATTILA;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_ALLERGIMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_HUDMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.BETA_HUDMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.AJLA_DOKTOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.ALVA_VARDADMINISTRATOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.ajlaDoctorBuilder;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate.CertificateBuilder;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate;

class CertificateActionSendTest {

  private CertificateActionSend certificateActionSend;
  private ActionEvaluation.ActionEvaluationBuilder actionEvaluationBuilder;
  private CertificateBuilder certificateBuilder;
  private static final CertificateActionSpecification CERTIFICATE_ACTION_SPECIFICATION =
      CertificateActionSpecification.builder()
          .certificateActionType(CertificateActionType.SEND)
          .build();

  @BeforeEach
  void setUp() {
    certificateActionSend = (CertificateActionSend) CertificateActionFactory.create(
        CERTIFICATE_ACTION_SPECIFICATION);

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
  void shallReturnTypeFromSpecification() {
    assertEquals(CertificateActionType.SEND, certificateActionSend.getType());
  }

  @Test
  void shallReturnFalseIfCertificateIsEmpty() {
    final Optional<Certificate> certificate = Optional.empty();
    final var actionEvaluation = actionEvaluationBuilder.build();

    assertFalse(
        certificateActionSend.evaluate(certificate, actionEvaluation),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnFalseIfNotDoctor() {
    final var actionEvaluation = actionEvaluationBuilder
        .user(ALVA_VARDADMINISTRATOR)
        .build();

    final var certificate = certificateBuilder.build();

    assertFalse(
        certificateActionSend.evaluate(Optional.of(certificate), actionEvaluation),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnTrueIfDoctor() {
    final var actionEvaluation = actionEvaluationBuilder
        .build();

    final var certificate = certificateBuilder.build();

    assertTrue(
        certificateActionSend.evaluate(Optional.of(certificate), actionEvaluation),
        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnTrueIfSigned() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    final var certificate = certificateBuilder
        .status(Status.SIGNED)
        .build();

    assertTrue(
        certificateActionSend.evaluate(Optional.of(certificate), actionEvaluation),
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
        certificateActionSend.evaluate(Optional.of(certificate), actionEvaluation),
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
        certificateActionSend.evaluate(Optional.of(certificate), actionEvaluation),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnFalseIfRevoked() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    final var certificate = certificateBuilder
        .status(Status.REVOKED)
        .build();

    assertFalse(
        certificateActionSend.evaluate(Optional.of(certificate), actionEvaluation),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnTrueIfNotSent() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    final var certificate = certificateBuilder
        .status(Status.SIGNED)
        .build();

    assertTrue(
        certificateActionSend.evaluate(Optional.of(certificate), actionEvaluation),
        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnFalseIfSent() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    final var certificate = certificateBuilder
        .status(Status.SIGNED)
        .sent(TestDataCertificate.SENT)
        .build();

    assertFalse(
        certificateActionSend.evaluate(Optional.of(certificate), actionEvaluation),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnName() {
    assertEquals("Skicka till Försäkringskassan", certificateActionSend.getName());
  }

  @Test
  void shallReturnDescription() {
    assertEquals("Öppnar ett fönster där du kan välja att skicka intyget till Försäkringskassan.",
        certificateActionSend.getDescription());
  }

  @Test
  void shallReturnBody() {
    assertEquals(
        "<p>Om du går vidare kommer intyget skickas direkt till Försäkringskassans system vilket ska göras i samråd med patienten.</p>",
        certificateActionSend.getBody());
  }


  @Test
  void shallReturnReasonNotAllowedIfEvaluateReturnsFalse() {
    final var actionEvaluation = ActionEvaluation.builder()
        .patient(ANONYMA_REACT_ATTILA)
        .user(ALVA_VARDADMINISTRATOR)
        .build();

    final var actualResult = certificateActionSend.reasonNotAllowed(actionEvaluation);

    assertFalse(actualResult.isEmpty());
  }

  @Test
  void shallReturnEmptyListIfEvaluateReturnsTrue() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    final var certificate = certificateBuilder
        .status(Status.SIGNED)
        .build();

    final var actualResult = certificateActionSend.reasonNotAllowed(Optional.of(certificate),
        actionEvaluation);

    assertTrue(actualResult.isEmpty());
  }

  @Nested
  class AccessScope {

    private se.inera.intyg.certificateservice.domain.common.model.AccessScope userAccessScope;

    @BeforeEach
    void setUp() {
      certificateActionSend = (CertificateActionSend) CertificateActionFactory.create(
          CERTIFICATE_ACTION_SPECIFICATION);
      certificateBuilder = Certificate.builder()
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
          .subUnit(ALFA_ALLERGIMOTTAGNINGEN)
          .patient(ATHENA_REACT_ANDERSSON)
          .careProvider(ALFA_REGIONEN)
          .careUnit(ALFA_MEDICINCENTRUM);
    }

    @Nested
    class UserAccessScopeWithinCareUnit {

      @BeforeEach
      void setUp() {
        userAccessScope = se.inera.intyg.certificateservice.domain.common.model.AccessScope.WITHIN_CARE_UNIT;
      }

      @Test
      void shallReturnTrueIfWithinCareUnit() {
        final var actionEvaluation = actionEvaluationBuilder
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertTrue(
            certificateActionSend.evaluate(Optional.of(certificate), actionEvaluation),
            () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }

      @Test
      void shallReturnFalseIfNotWithinCareUnit() {
        final var actionEvaluation = actionEvaluationBuilder
            .subUnit(ALFA_HUDMOTTAGNINGEN)
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertFalse(
            certificateActionSend.evaluate(Optional.of(certificate), actionEvaluation),
            () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }
    }

    @Nested
    class UserAccessScopeWithinCareProvider {

      @BeforeEach
      void setUp() {
        userAccessScope = se.inera.intyg.certificateservice.domain.common.model.AccessScope.WITHIN_CARE_PROVIDER;
      }

      @Test
      void shallReturnTrueIfWithinCareUnit() {
        final var actionEvaluation = actionEvaluationBuilder
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertTrue(
            certificateActionSend.evaluate(Optional.of(certificate), actionEvaluation),
            () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }

      @Test
      void shallReturnFalseIfNotWithinCareUnit() {
        final var actionEvaluation = actionEvaluationBuilder
            .subUnit(ALFA_HUDMOTTAGNINGEN)
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertFalse(
            certificateActionSend.evaluate(Optional.of(certificate), actionEvaluation),
            () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }
    }

    @Nested
    class UserAccessScopeAllCareProviders {

      @BeforeEach
      void setUp() {
        userAccessScope = se.inera.intyg.certificateservice.domain.common.model.AccessScope.ALL_CARE_PROVIDERS;
      }

      @Test
      void shallReturnTrueIfWithinCareUnit() {
        final var actionEvaluation = actionEvaluationBuilder
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertTrue(
            certificateActionSend.evaluate(Optional.of(certificate), actionEvaluation),
            () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }

      @Test
      void shallReturnFalseIfNotWithinCareUnit() {
        final var actionEvaluation = actionEvaluationBuilder
            .subUnit(ALFA_HUDMOTTAGNINGEN)
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertFalse(
            certificateActionSend.evaluate(Optional.of(certificate), actionEvaluation),
            () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }

      @Test
      void shallReturnFalseIfNotWithinCareProvider() {
        final var actionEvaluation = actionEvaluationBuilder
            .careUnit(BETA_VARDCENTRAL)
            .subUnit(BETA_HUDMOTTAGNINGEN)
            .careProvider(BETA_REGIONEN)
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertFalse(
            certificateActionSend.evaluate(Optional.of(certificate), actionEvaluation),
            () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }
    }
  }
}
