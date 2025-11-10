package se.inera.intyg.certificateservice.domain.action.certificate.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateservice.domain.common.model.Role.CARE_ADMIN;
import static se.inera.intyg.certificateservice.domain.common.model.Role.DENTIST;
import static se.inera.intyg.certificateservice.domain.common.model.Role.DOCTOR;
import static se.inera.intyg.certificateservice.domain.common.model.Role.MIDWIFE;
import static se.inera.intyg.certificateservice.domain.common.model.Role.NURSE;
import static se.inera.intyg.certificateservice.domain.common.model.Role.PRIVATE_DOCTOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.ALFA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.BETA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_MEDICINCENTRUM;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_VARDCENTRAL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.BETA_VARDCENTRAL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_VARDCENTRAL_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.ag7804CertificateBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7804CertificateBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModel.ag7804certificateModelBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ANONYMA_REACT_ATTILA;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATLAS_REACT_ABRAHAMSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_ALLERGIMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_HUDMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.BETA_HUDMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_HUDMOTTAGNINGEN_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.AJLA_DOKTOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.ajlaDoctorBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.alvaVardadministratorBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.danDentistBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AGREEMENT_FALSE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALLOW_COPY_FALSE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.BLOCKED_TRUE;

import java.time.LocalDateTime;
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
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionContentProvider;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.common.model.AccessScope;
import se.inera.intyg.certificateservice.domain.common.model.CertificatesRequest;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.unit.model.SubUnit;

@ExtendWith(MockitoExtension.class)
class CertificateActionUpdateDraftFromCertificateTest {

  private CertificateActionUpdateDraftFromCertificate certificateAction;
  private ActionEvaluation.ActionEvaluationBuilder actionEvaluationBuilder;
  private MedicalCertificate.MedicalCertificateBuilder certificateBuilder;

  @Mock
  CertificateActionContentProvider certificateActionContentProvider;
  @Mock
  CertificateRepository certificateRepository;
  @InjectMocks
  CertificateActionFactory certificateActionFactory;

  @BeforeEach
  void setUp() {
    final var specification = CertificateActionSpecification.builder()
        .certificateActionType(CertificateActionType.UPDATE_DRAFT_FROM_CERTIFICATE)
        .allowedRoles(
            List.of(DOCTOR, PRIVATE_DOCTOR, DENTIST, NURSE, MIDWIFE, CARE_ADMIN)
        )
        .contentProvider(certificateActionContentProvider)
        .build();

    certificateAction = (CertificateActionUpdateDraftFromCertificate) certificateActionFactory.create(
        specification);

    certificateBuilder = ag7804CertificateBuilder()
        .status(Status.DRAFT)
        .certificateRepository(certificateRepository);

    lenient().when(certificateRepository.findByCertificatesRequest(any(CertificatesRequest.class)))
        .thenReturn(List.of(fk7804CertificateBuilder().build()));

    actionEvaluationBuilder = ActionEvaluation.builder()
        .user(
            ajlaDoctorBuilder()
                .accessScope(AccessScope.ALL_CARE_PROVIDERS)
                .build()
        )
        .subUnit(ALFA_ALLERGIMOTTAGNINGEN)
        .patient(ATHENA_REACT_ANDERSSON)
        .careProvider(ALFA_REGIONEN)
        .careUnit(ALFA_MEDICINCENTRUM);
  }

  @Test
  void shouldReturnType() {
    assertEquals(CertificateActionType.UPDATE_DRAFT_FROM_CERTIFICATE,
        certificateAction.getType());
  }

  @Test
  void shouldReturnName() {
    final var expected = "expectedName";

    when(certificateActionContentProvider.name(null)).thenReturn(expected);

    final var actual = certificateAction.getName(Optional.empty());

    assertEquals(expected, actual);
  }

  @Test
  void shouldReturnDescription() {
    final var expected = "expectedDescription";

    when(certificateActionContentProvider.description(null)).thenReturn(expected);

    final var actual = certificateAction.getDescription(Optional.empty());

    assertEquals(expected, actual);
  }

  @Test
  void shouldReturnBody() {
    final var expected = "expectedBody";

    when(certificateActionContentProvider.body(null)).thenReturn(expected);

    final var actual = certificateAction.getBody(Optional.empty(),
        Optional.empty());

    assertEquals(expected, actual);
  }

  @Test
  void shallReturnFalseIfUserIsCareAdminAndPatientIsProtectedPerson() {
    final var actionEvaluation = actionEvaluationBuilder
        .user(
            alvaVardadministratorBuilder()
                .accessScope(AccessScope.ALL_CARE_PROVIDERS)
                .build()
        )
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
        certificateAction.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
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
        certificateAction.evaluate(Optional.of(certificate), Optional.of(actionEvaluation)),
        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnFalseIfCertificateIsEmpty() {
    final Optional<Certificate> certificate = Optional.empty();
    final var actionEvaluation = actionEvaluationBuilder.build();

    assertFalse(
        certificateAction.evaluate(certificate,
            Optional.of(actionEvaluation)),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnTrueIfIssuedUnitMatchesSubUnit() {
    final var actionEvaluation = actionEvaluationBuilder
        .subUnit(
            SubUnit.builder()
                .hsaId(new HsaId(ALFA_ALLERGIMOTTAGNINGEN_ID))
                .build()
        )
        .build();

    final var certificate = certificateBuilder.build();

    assertTrue(
        certificateAction.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnTrueIfCareUnitMatchesSubUnit() {
    final var actionEvaluation = actionEvaluationBuilder
        .subUnit(
            SubUnit.builder()
                .hsaId(new HsaId(ALFA_MEDICINCENTRUM_ID))
                .build()
        )
        .build();

    final var certificate = certificateBuilder.build();

    assertTrue(
        certificateAction.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnFalseIfIssuedUnitDontMatchSubUnit() {
    final var actionEvaluation = actionEvaluationBuilder
        .careUnit(ALFA_VARDCENTRAL)
        .subUnit(
            SubUnit.builder()
                .hsaId(new HsaId(ALFA_HUDMOTTAGNINGEN_ID))
                .build()
        )
        .build();

    final var certificate = certificateBuilder.build();

    assertFalse(
        certificateAction.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnFalseIfCareUnitDontMatchSubUnitAndSubUnitDontMatchIssuingUnit() {
    final var actionEvaluation = actionEvaluationBuilder
        .careUnit(ALFA_VARDCENTRAL)
        .subUnit(
            SubUnit.builder()
                .hsaId(new HsaId(ALFA_VARDCENTRAL_ID))
                .build()
        )
        .build();

    final var certificate = certificateBuilder.build();

    assertFalse(
        certificateAction.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnTrueIfDentist() {
    final var actionEvaluation = actionEvaluationBuilder
        .user(
            danDentistBuilder()
                .accessScope(AccessScope.ALL_CARE_PROVIDERS)
                .build()
        )
        .build();

    final var certificate = certificateBuilder.build();

    assertTrue(
        certificateAction.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnTrueIfDoctor() {
    final var actionEvaluation = actionEvaluationBuilder
        .build();

    final var certificate = certificateBuilder.build();

    assertTrue(
        certificateAction.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnFalseIfSigned() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    final var certificate = certificateBuilder
        .status(Status.SIGNED)
        .build();

    assertFalse(
        certificateAction.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
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
        certificateAction.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnFalseIfDeletedDraft() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    final var certificate = certificateBuilder
        .status(Status.DELETED_DRAFT)
        .build();

    assertFalse(
        certificateAction.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnTrueIfDraft() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    final var certificate = certificateBuilder
        .status(Status.DRAFT)
        .build();

    assertTrue(
        certificateAction.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnFalseIfUserIsBlocked() {
    final var actionEvaluation = actionEvaluationBuilder
        .user(
            ajlaDoctorBuilder()
                .blocked(BLOCKED_TRUE)
                .build()
        )
        .build();

    final var certificate = certificateBuilder.build();

    assertFalse(
        certificateAction.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnFalseIfUserHasAllowCopyFalse() {
    final var actionEvaluation = actionEvaluationBuilder
        .user(
            ajlaDoctorBuilder()
                .allowCopy(ALLOW_COPY_FALSE)
                .build()
        )
        .build();

    final var certificate = certificateBuilder.build();

    assertFalse(
        certificateAction.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnReasonNotAllowedIfEvaluateReturnsFalse() {
    final var actualResult = certificateAction.reasonNotAllowed(
        Optional.of(certificateBuilder.build()), Optional.empty());

    assertFalse(actualResult.isEmpty());
  }

  @Test
  void shallReturnEmptyListIfEvaluateReturnsTrue() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    final var certificate = certificateBuilder
        .build();

    final var actualResult = certificateAction.reasonNotAllowed(
        Optional.of(certificate),
        Optional.of(actionEvaluation));

    assertTrue(actualResult.isEmpty());
  }

  @Test
  void shallReturnFalseIfPatientNotAlive() {
    final var certificate = certificateBuilder
        .status(Status.SIGNED)
        .sent(null)
        .certificateMetaData(
            CertificateMetaData.builder()
                .issuingUnit(ALFA_ALLERGIMOTTAGNINGEN)
                .careUnit(ALFA_MEDICINCENTRUM)
                .careProvider(ALFA_REGIONEN)
                .patient(ATLAS_REACT_ABRAHAMSSON)
                .build()
        )
        .build();

    final var actionEvaluation = ActionEvaluation.builder()
        .user(AJLA_DOKTOR)
        .subUnit(ALFA_ALLERGIMOTTAGNINGEN)
        .patient(ATLAS_REACT_ABRAHAMSSON)
        .careProvider(ALFA_REGIONEN)
        .careUnit(ALFA_MEDICINCENTRUM)
        .build();

    assertFalse(
        certificateAction.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnFalseIfUserMissingAgreement() {
    final var actionEvaluation = actionEvaluationBuilder
        .user(
            ajlaDoctorBuilder()
                .agreement(AGREEMENT_FALSE)
                .build()
        )
        .build();

    final var certificate = certificateBuilder.build();

    assertFalse(
        certificateAction.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Nested
  class UserAccessScopeWithinCareUnit {

    private AccessScope userAccessScope;

    @BeforeEach
    void setUp() {
      userAccessScope = AccessScope.WITHIN_CARE_UNIT;
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
          certificateAction.evaluate(Optional.of(certificate),
              Optional.of(actionEvaluation)),
          () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
      );
    }
  }

  @Nested
  class UserAccessScopeWithinCareProvider {

    private AccessScope userAccessScope;

    @BeforeEach
    void setUp() {
      userAccessScope = AccessScope.WITHIN_CARE_PROVIDER;
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
          certificateAction.evaluate(Optional.of(certificate),
              Optional.of(actionEvaluation)),
          () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
      );
    }

    @Test
    void shallReturnFalseIfNotWithinCareUnit() {
      final var actionEvaluation = actionEvaluationBuilder
          .careUnit(ALFA_VARDCENTRAL)
          .subUnit(ALFA_HUDMOTTAGNINGEN)
          .user(ajlaDoctorBuilder()
              .accessScope(userAccessScope)
              .build())
          .build();

      final var certificate = certificateBuilder.build();

      assertFalse(
          certificateAction.evaluate(Optional.of(certificate),
              Optional.of(actionEvaluation)),
          () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
      );
    }
  }

  @Nested
  class UserAccessScopeAllCareProviders {

    private AccessScope userAccessScope;

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
          certificateAction.evaluate(Optional.of(certificate),
              Optional.of(actionEvaluation)),
          () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
      );
    }

    @Test
    void shallReturnFalseIfNotWithinCareUnit() {
      final var actionEvaluation = actionEvaluationBuilder
          .careUnit(ALFA_VARDCENTRAL)
          .subUnit(ALFA_HUDMOTTAGNINGEN)
          .user(ajlaDoctorBuilder()
              .accessScope(userAccessScope)
              .build())
          .build();

      final var certificate = certificateBuilder.build();

      assertFalse(
          certificateAction.evaluate(Optional.of(certificate),
              Optional.of(actionEvaluation)),
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
          certificateAction.evaluate(Optional.of(certificate),
              Optional.of(actionEvaluation)),
          () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
      );
    }
  }

  @Nested
  class ActiveCertificateTests {

    @Test
    void shouldReturnFalseIfCertificateIsInactive() {
      final var actionEvaluation = actionEvaluationBuilder
          .build();

      final var certificate = certificateBuilder
          .certificateModel(
              ag7804certificateModelBuilder()
                  .activeFrom(LocalDateTime.now().plusDays(1))
                  .build()
          )
          .build();

      assertFalse(
          certificateAction.evaluate(Optional.of(certificate),
              Optional.of(actionEvaluation)),
          () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
      );
    }

    @Test
    void shouldReturnTrueIfCertificateIsActive() {
      final var actionEvaluation = actionEvaluationBuilder
          .build();

      final var certificate = certificateBuilder
          .certificateModel(
              ag7804certificateModelBuilder()
                  .activeFrom(LocalDateTime.now().minusDays(1))
                  .build()
          )
          .build();

      assertTrue(
          certificateAction.evaluate(Optional.of(certificate),
              Optional.of(actionEvaluation)),
          () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
      );
    }
  }
}