package se.inera.intyg.certificateservice.domain.action.certificate.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataAction.actionEvaluationBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.ALFA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.BETA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_MEDICINCENTRUM;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.BETA_VARDCENTRAL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_ALLERGIMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_HUDMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.BETA_HUDMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.AJLA_DOKTOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.ALVA_VARDADMINISTRATOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.ajlaDoctorBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.alvaVardadministratorBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.annaSjukskoterskaBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.bertilBarnmorskaBuilder;

import java.util.List;
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
import se.inera.intyg.certificateservice.domain.common.model.AccessScope;
import se.inera.intyg.certificateservice.domain.common.model.Role;

@ExtendWith(MockitoExtension.class)
class CertificateActionResponsibleIssuerTest {

  private CertificateActionResponsibleIssuer certificateActionResponsibleIssuer;
  private ActionEvaluation.ActionEvaluationBuilder actionEvaluationBuilder;
  private CertificateBuilder certificateBuilder;
  private static final List<Role> ALLOWED_ROLES = List.of(Role.NURSE, Role.CARE_ADMIN,
      Role.MIDWIFE);
  private static final CertificateActionSpecification CERTIFICATE_ACTION_SPECIFICATION =
      CertificateActionSpecification.builder()
          .certificateActionType(CertificateActionType.RESPONSIBLE_ISSUER)
          .allowedRoles(ALLOWED_ROLES)
          .build();
  @Mock
  CertificateUnitAccessEvaluationRepository certificateUnitAccessEvaluationRepository;
  @InjectMocks
  CertificateActionFactory certificateActionFactory;

  @BeforeEach
  void setUp() {
    certificateActionResponsibleIssuer = (CertificateActionResponsibleIssuer) certificateActionFactory.create(
        CERTIFICATE_ACTION_SPECIFICATION);

    certificateBuilder = Certificate.builder()
        .status(Status.DRAFT)
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
        .user(ALVA_VARDADMINISTRATOR)
        .subUnit(ALFA_ALLERGIMOTTAGNINGEN)
        .patient(ATHENA_REACT_ANDERSSON)
        .careProvider(ALFA_REGIONEN)
        .careUnit(ALFA_MEDICINCENTRUM);
  }

  @Test
  void shallReturnName() {
    assertEquals(
        "Ansvarig intygsutfärdare",
        certificateActionResponsibleIssuer.getName(Optional.empty()));
  }

  @Test
  void shallReturnType() {
    assertEquals(
        CertificateActionType.RESPONSIBLE_ISSUER,
        certificateActionResponsibleIssuer.getType());
  }

  @Test
  void shallReturnFalseIfDoctor() {
    final var actionEvaluation = actionEvaluationBuilder
        .user(AJLA_DOKTOR)
        .build();

    final var certificate = certificateBuilder.build();

    assertFalse(
        certificateActionResponsibleIssuer.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnTrueIfNurse() {
    final var actionEvaluation = actionEvaluationBuilder
        .user(annaSjukskoterskaBuilder()
            .accessScope(AccessScope.ALL_CARE_PROVIDERS)
            .build())
        .build();

    final var certificate = certificateBuilder.build();

    assertTrue(
        certificateActionResponsibleIssuer.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnTrueIfMidwife() {
    final var actionEvaluation = actionEvaluationBuilder
        .user(bertilBarnmorskaBuilder()
            .accessScope(AccessScope.ALL_CARE_PROVIDERS)
            .build())
        .build();

    final var certificate = certificateBuilder.build();

    assertTrue(
        certificateActionResponsibleIssuer.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnTrueIfCareAdmin() {
    final var actionEvaluation = actionEvaluationBuilder
        .user(alvaVardadministratorBuilder()
            .accessScope(AccessScope.ALL_CARE_PROVIDERS)
            .build())
        .build();

    final var certificate = certificateBuilder.build();

    assertTrue(
        certificateActionResponsibleIssuer.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnTrueIfDraft() {
    final var actionEvaluation = actionEvaluationBuilder
        .user(alvaVardadministratorBuilder()
            .accessScope(AccessScope.ALL_CARE_PROVIDERS)
            .build())
        .build();

    final var certificate = certificateBuilder
        .status(Status.DRAFT)
        .build();

    assertTrue(
        certificateActionResponsibleIssuer.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnFalseIfNotDraft() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    final var certificate = certificateBuilder
        .status(Status.SIGNED)
        .build();

    assertFalse(
        certificateActionResponsibleIssuer.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Nested
  class UserAccessScope {

    private se.inera.intyg.certificateservice.domain.common.model.AccessScope userAccessScope;

    @Nested
    class UserAccessScopeWithinCareProvider {

      @BeforeEach
      void setUp() {
        userAccessScope = se.inera.intyg.certificateservice.domain.common.model.AccessScope.WITHIN_CARE_PROVIDER;
      }

      @Test
      void shallReturnFalseIfNotWithinCareUnit() {
        final var actionEvaluation = actionEvaluationBuilder()
            .subUnit(ALFA_HUDMOTTAGNINGEN)
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertFalse(
            certificateActionResponsibleIssuer.evaluate(Optional.of(certificate),
                Optional.of(actionEvaluation)),
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
      void shallReturnFalseIfNotWithinCareUnit() {
        final var actionEvaluation = actionEvaluationBuilder()
            .subUnit(ALFA_HUDMOTTAGNINGEN)
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertFalse(
            certificateActionResponsibleIssuer.evaluate(Optional.of(certificate),
                Optional.of(actionEvaluation)),
            () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }

      @Test
      void shallReturnFalseIfNotWithinCareProvider() {
        final var actionEvaluation = actionEvaluationBuilder()
            .careUnit(BETA_VARDCENTRAL)
            .subUnit(BETA_HUDMOTTAGNINGEN)
            .careProvider(BETA_REGIONEN)
            .user(ajlaDoctorBuilder()
                .accessScope(userAccessScope)
                .build())
            .build();

        final var certificate = certificateBuilder.build();

        assertFalse(
            certificateActionResponsibleIssuer.evaluate(Optional.of(certificate),
                Optional.of(actionEvaluation)),
            () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
        );
      }
    }
  }
}

