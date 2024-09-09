package se.inera.intyg.certificateservice.domain.action.certificate.model;

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
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.ANNA_SJUKSKOTERKSA;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.BERTIL_BARNMORSKA;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.DAN_DENTIST;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.ajlaDoctorBuilder;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate.CertificateBuilder;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateUnitAccessEvaluationRepository;

@ExtendWith(MockitoExtension.class)
class CertificateActionReadTest {

  private CertificateActionRead certificateActionRead;
  private ActionEvaluation.ActionEvaluationBuilder actionEvaluationBuilder;
  private CertificateBuilder certificateBuilder;

  private static final CertificateActionSpecification CERTIFICATE_ACTION_SPECIFICATION =
      CertificateActionSpecification.builder()
          .certificateActionType(CertificateActionType.READ)
          .build();
  @Mock
  CertificateUnitAccessEvaluationRepository certificateUnitAccessEvaluationRepository;
  @InjectMocks
  CertificateActionFactory certificateActionFactory;

  @BeforeEach
  void setUp() {
    certificateActionRead = (CertificateActionRead) certificateActionFactory.create(
        CERTIFICATE_ACTION_SPECIFICATION);
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
    assertEquals(CertificateActionType.READ, certificateActionRead.getType());
  }

  @Test
  void shallReturnFalseIfCertificateIsEmpty() {
    final Optional<Certificate> certificate = Optional.empty();
    final var actionEvaluation = actionEvaluationBuilder.build();

    assertFalse(
        certificateActionRead.evaluate(certificate, Optional.of(actionEvaluation)),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnFalseIfUserIsCareAdminAndPatientIsProtectedPerson() {
    final var actionEvaluation = actionEvaluationBuilder
        .user(ALVA_VARDADMINISTRATOR)
        .build();

    final var certificate = certificateBuilder
        .certificateMetaData(
            CertificateMetaData.builder()
                .issuingUnit(ALFA_ALLERGIMOTTAGNINGEN)
                .careUnit(ALFA_MEDICINCENTRUM)
                .careProvider(ALFA_REGIONEN)
                .patient(ANONYMA_REACT_ATTILA)
                .build()
        )
        .build();

    assertFalse(
        certificateActionRead.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnTrueIfUserIsDoctorAndPatientIsProtectedPerson() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    final var certificate = certificateBuilder
        .certificateMetaData(
            CertificateMetaData.builder()
                .issuingUnit(ALFA_ALLERGIMOTTAGNINGEN)
                .careUnit(ALFA_MEDICINCENTRUM)
                .careProvider(ALFA_REGIONEN)
                .patient(ANONYMA_REACT_ATTILA)
                .build()
        )
        .build();

    assertTrue(
        certificateActionRead.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnReasonNotAllowedIfEvaluateReturnsFalse() {
    final var actualResult = certificateActionRead.reasonNotAllowed(
        Optional.of(certificateBuilder.build()), Optional.empty());

    assertFalse(actualResult.isEmpty());
  }

  @Test
  void shallReturnEmptyListIfEvaluateReturnsTrue() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    final var certificate = certificateBuilder
        .status(Status.DRAFT)
        .build();

    final var actualResult = certificateActionRead.reasonNotAllowed(Optional.of(certificate),
        Optional.of(actionEvaluation));

    assertTrue(actualResult.isEmpty());
  }

  @Nested
  class EvaluateActionRuleRole {

    @Test
    void shallReturnFalseIfDentist() {
      final var actionEvaluation = ActionEvaluation.builder()
          .patient(ATHENA_REACT_ANDERSSON)
          .user(DAN_DENTIST)
          .subUnit(ALFA_ALLERGIMOTTAGNINGEN)
          .build();

      final var actualResult = certificateActionRead.evaluate(
          Optional.of(certificateBuilder.build()), Optional.of(actionEvaluation));

      assertFalse(actualResult);
    }

    @Test
    void shallReturnTrueIfCareAdmin() {
      final var actionEvaluation = ActionEvaluation.builder()
          .patient(ATHENA_REACT_ANDERSSON)
          .user(ALVA_VARDADMINISTRATOR)
          .subUnit(ALFA_ALLERGIMOTTAGNINGEN)
          .build();

      final var actualResult = certificateActionRead.evaluate(
          Optional.of(certificateBuilder.build()), Optional.of(actionEvaluation));

      assertTrue(actualResult);
    }

    @Test
    void shallReturnTrueIfDoctor() {
      final var actionEvaluation = ActionEvaluation.builder()
          .patient(ATHENA_REACT_ANDERSSON)
          .user(AJLA_DOKTOR)
          .subUnit(ALFA_ALLERGIMOTTAGNINGEN)
          .build();

      final var actualResult = certificateActionRead.evaluate(
          Optional.of(certificateBuilder.build()), Optional.of(actionEvaluation));

      assertTrue(actualResult);
    }

    @Test
    void shallReturnTrueIfNurse() {
      final var actionEvaluation = ActionEvaluation.builder()
          .patient(ATHENA_REACT_ANDERSSON)
          .user(ANNA_SJUKSKOTERKSA)
          .subUnit(ALFA_ALLERGIMOTTAGNINGEN)
          .build();

      final var actualResult = certificateActionRead.evaluate(
          Optional.of(certificateBuilder.build()), Optional.of(actionEvaluation));

      assertTrue(actualResult);
    }

    @Test
    void shallReturnTrueIfMidwife() {
      final var actionEvaluation = ActionEvaluation.builder()
          .patient(ATHENA_REACT_ANDERSSON)
          .user(BERTIL_BARNMORSKA)
          .subUnit(ALFA_ALLERGIMOTTAGNINGEN)
          .build();

      final var actualResult = certificateActionRead.evaluate(
          Optional.of(certificateBuilder.build()), Optional.of(actionEvaluation));

      assertTrue(actualResult);
    }
  }

  @Nested
  class AccessScope {

    private se.inera.intyg.certificateservice.domain.common.model.AccessScope userAccessScope;

    @BeforeEach
    void setUp() {
      certificateActionRead = (CertificateActionRead) certificateActionFactory.create(
          CERTIFICATE_ACTION_SPECIFICATION);
      certificateBuilder = Certificate.builder()
          .status(Status.DRAFT)
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
            certificateActionRead.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
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
            certificateActionRead.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
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
            certificateActionRead.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
            () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }

      @Test
      void shallReturnTrueIfWithinCareProvider() {
        final var actionEvaluation = actionEvaluationBuilder
            .subUnit(ALFA_HUDMOTTAGNINGEN)
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertTrue(
            certificateActionRead.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
            () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
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
            certificateActionRead.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
            () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }

      @Test
      void shallReturnTrueIfWithinCareProvider() {
        final var actionEvaluation = actionEvaluationBuilder
            .subUnit(ALFA_HUDMOTTAGNINGEN)
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertTrue(
            certificateActionRead.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
            () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }

      @Test
      void shallReturnTrueIfOutsideOfCareProvider() {
        final var actionEvaluation = actionEvaluationBuilder
            .careUnit(BETA_VARDCENTRAL)
            .subUnit(BETA_HUDMOTTAGNINGEN)
            .careProvider(BETA_REGIONEN)
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertTrue(
            certificateActionRead.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
            () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }
    }
  }
}
