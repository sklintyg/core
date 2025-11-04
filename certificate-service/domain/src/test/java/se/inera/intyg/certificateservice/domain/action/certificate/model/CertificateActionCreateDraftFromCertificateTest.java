package se.inera.intyg.certificateservice.domain.action.certificate.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.ALFA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_MEDICINCENTRUM;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_VARDCENTRAL_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7210CertificateBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7804CertificateBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModel.fk7804certificateModelBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATLAS_REACT_ABRAHAMSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataRelation.relationComplementBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataRelation.relationReplaceBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_ALLERGIMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_HUDMOTTAGNINGEN_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.AJLA_DOKTOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.DAN_DENTIST;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.ajlaDoctorBuilder;
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
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionContentProvider;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateActionConfigurationRepository;
import se.inera.intyg.certificateservice.domain.common.model.AccessScope;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.common.model.Role;
import se.inera.intyg.certificateservice.domain.unit.model.SubUnit;

@ExtendWith(MockitoExtension.class)
class CertificateActionCreateDraftFromCertificateTest {

  private static final String AG7804_BODY = """
      <div><div class="ic-alert ic-alert--status ic-alert--info">
      <i class="ic-alert__icon ic-info-icon"></i>
      Kom ihåg att stämma av med patienten om hen vill att du skickar Läkarintyget för sjukpenning till Försäkringskassan. Gör detta i så fall först.</div>
      <p class='iu-pt-400'>Skapa ett Läkarintyg om arbetsförmåga - arbetsgivaren (AG7804) utifrån ett Läkarintyg för sjukpenning innebär att informationsmängder som är gemensamma för båda intygen automatiskt förifylls.
      </p></div>
      """;
  private static final String AG7804_DESCRIPTION = "Skapar ett intyg till arbetsgivaren utifrån Försäkringskassans intyg.";
  private static final String AG7804_NAME = "Skapa AG7804";

  private CertificateActionCreateDraftFromCertificate certificateActionCreateDraftFromCertificate;
  private ActionEvaluation.ActionEvaluationBuilder actionEvaluationBuilder;
  private MedicalCertificate.MedicalCertificateBuilder certificateBuilder;
  @Mock
  CertificateActionContentProvider certificateActionContentProvider;
  @Mock
  CertificateActionConfigurationRepository certificateActionConfigurationRepository;
  @InjectMocks
  CertificateActionFactory certificateActionFactory;

  private final CertificateActionSpecification certificateActionSpecification =
      CertificateActionSpecification.builder()
          .certificateActionType(CertificateActionType.CREATE_DRAFT_FROM_CERTIFICATE)
          .allowedRoles(List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.DENTIST, Role.NURSE,
              Role.MIDWIFE,
              Role.CARE_ADMIN))
          .build();

  @BeforeEach
  void setUp() {
    certificateActionCreateDraftFromCertificate = (CertificateActionCreateDraftFromCertificate) certificateActionFactory.create(
        certificateActionSpecification);

    certificateBuilder = fk7804CertificateBuilder()
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
  void shouldReturnType() {
    assertEquals(CertificateActionType.CREATE_DRAFT_FROM_CERTIFICATE,
        certificateActionCreateDraftFromCertificate.getType());
  }

  @Test
  void shouldReturnName() {
    certificateActionCreateDraftFromCertificate = (CertificateActionCreateDraftFromCertificate) certificateActionFactory.create(
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.CREATE_DRAFT_FROM_CERTIFICATE)
            .contentProvider(certificateActionContentProvider)
            .build()
    );

    when(certificateActionContentProvider.name(null)).thenReturn(AG7804_NAME);

    assertEquals(AG7804_NAME,
        certificateActionCreateDraftFromCertificate.getName(Optional.empty()));
  }

  @Test
  void shouldReturnDescription() {
    certificateActionCreateDraftFromCertificate = (CertificateActionCreateDraftFromCertificate) certificateActionFactory.create(
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.CREATE_DRAFT_FROM_CERTIFICATE)
            .contentProvider(certificateActionContentProvider)
            .build()
    );

    when(certificateActionContentProvider.description(null)).thenReturn(AG7804_DESCRIPTION);

    assertEquals(
        AG7804_DESCRIPTION,
        certificateActionCreateDraftFromCertificate.getDescription(Optional.empty()));
  }

  @Test
  void shouldReturnBody() {
    certificateActionCreateDraftFromCertificate = (CertificateActionCreateDraftFromCertificate) certificateActionFactory.create(
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.CREATE_DRAFT_FROM_CERTIFICATE)
            .contentProvider(certificateActionContentProvider)
            .build()
    );

    when(certificateActionContentProvider.body(null)).thenReturn(AG7804_BODY);

    assertEquals(
        AG7804_BODY,
        certificateActionCreateDraftFromCertificate.getBody(Optional.empty(), Optional.empty())
    );
  }

  @Test
  void shallReturnFalseIfCertificateIsEmpty() {
    final Optional<Certificate> certificate = Optional.empty();
    final var actionEvaluation = actionEvaluationBuilder.build();

    assertFalse(
        certificateActionCreateDraftFromCertificate.evaluate(certificate,
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
        certificateActionCreateDraftFromCertificate.evaluate(Optional.of(certificate),
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
        certificateActionCreateDraftFromCertificate.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnFalseIfIssuedUnitDontMatchSubUnit() {
    final var actionEvaluation = actionEvaluationBuilder
        .subUnit(
            SubUnit.builder()
                .hsaId(new HsaId(ALFA_HUDMOTTAGNINGEN_ID))
                .build()
        )
        .build();

    final var certificate = certificateBuilder.build();

    assertFalse(
        certificateActionCreateDraftFromCertificate.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnFalseIfCareUnitDontMatchSubUnitAndSubUnitDontMatchIssuingUnit() {
    final var actionEvaluation = actionEvaluationBuilder
        .subUnit(
            SubUnit.builder()
                .hsaId(new HsaId(ALFA_VARDCENTRAL_ID))
                .build()
        )
        .build();

    final var certificate = certificateBuilder.build();

    assertFalse(
        certificateActionCreateDraftFromCertificate.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnTrueIfDentist() {
    final var actionEvaluation = actionEvaluationBuilder
        .user(DAN_DENTIST)
        .build();

    final var certificate = certificateBuilder.build();

    assertTrue(
        certificateActionCreateDraftFromCertificate.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnTrueIfDoctor() {
    final var actionEvaluation = actionEvaluationBuilder
        .build();

    final var certificate = certificateBuilder.build();

    assertTrue(
        certificateActionCreateDraftFromCertificate.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
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
        certificateActionCreateDraftFromCertificate.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnTrueIfRevoked() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    final var certificate = certificateBuilder
        .status(Status.REVOKED)
        .build();

    assertFalse(
        certificateActionCreateDraftFromCertificate.evaluate(Optional.of(certificate),
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
        certificateActionCreateDraftFromCertificate.evaluate(Optional.of(certificate),
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
        certificateActionCreateDraftFromCertificate.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
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
        certificateActionCreateDraftFromCertificate.evaluate(Optional.of(certificate),
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
        certificateActionCreateDraftFromCertificate.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnReasonNotAllowedIfEvaluateReturnsFalse() {
    final var actualResult = certificateActionCreateDraftFromCertificate.reasonNotAllowed(
        Optional.of(certificateBuilder.build()), Optional.empty());

    assertFalse(actualResult.isEmpty());
  }

  @Test
  void shallReturnEmptyListIfEvaluateReturnsTrue() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    final var certificate = certificateBuilder
        .status(Status.SIGNED)
        .build();

    final var actualResult = certificateActionCreateDraftFromCertificate.reasonNotAllowed(
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
        certificateActionCreateDraftFromCertificate.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnTrueIfReplacedByDraftCertificate() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    final var certificate = certificateBuilder
        .children(
            List.of(
                relationReplaceBuilder()
                    .build()
            )
        )
        .build();

    assertTrue(
        certificateActionCreateDraftFromCertificate.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnTrueIfReplacedBySignedCertificate() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    final var certificate = certificateBuilder
        .children(
            List.of(
                relationReplaceBuilder()
                    .certificate(
                        fk7210CertificateBuilder()
                            .status(Status.SIGNED)
                            .build()
                    )
                    .build()
            )
        )
        .build();

    assertFalse(
        certificateActionCreateDraftFromCertificate.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnTrueIfReplacedByRevokedCertificate() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    final var certificate = certificateBuilder
        .children(
            List.of(
                relationReplaceBuilder()
                    .certificate(
                        fk7210CertificateBuilder()
                            .status(Status.REVOKED)
                            .build()
                    )
                    .build()
            )
        )
        .build();

    assertTrue(
        certificateActionCreateDraftFromCertificate.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnTrueIfComplementedByDraftCertificate() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    final var certificate = certificateBuilder
        .children(
            List.of(
                relationComplementBuilder()
                    .build()
            )
        )
        .build();

    assertTrue(
        certificateActionCreateDraftFromCertificate.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnTrueIfComplementedBySignedCertificate() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    final var certificate = certificateBuilder
        .children(
            List.of(
                relationComplementBuilder()
                    .certificate(
                        fk7210CertificateBuilder()
                            .status(Status.SIGNED)
                            .build()
                    )
                    .build()
            )
        )
        .build();

    assertFalse(
        certificateActionCreateDraftFromCertificate.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnTrueIfComplementedByRevokedCertificate() {
    final var actionEvaluation = actionEvaluationBuilder.build();

    final var certificate = certificateBuilder
        .children(
            List.of(
                relationComplementBuilder()
                    .certificate(
                        fk7210CertificateBuilder()
                            .status(Status.REVOKED)
                            .build()
                    )
                    .build()
            )
        )
        .build();

    assertTrue(
        certificateActionCreateDraftFromCertificate.evaluate(Optional.of(certificate),
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
        certificateActionCreateDraftFromCertificate.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnFalseIfAccessScopeIsNotWithinCareUnit() {
    final var actionEvaluation = actionEvaluationBuilder
        .user(
            ajlaDoctorBuilder()
                .accessScope(AccessScope.WITHIN_CARE_PROVIDER)
                .build()
        )
        .build();

    final var certificate = certificateBuilder.build();

    assertFalse(
        certificateActionCreateDraftFromCertificate.evaluate(Optional.of(certificate),
            Optional.of(actionEvaluation)),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Nested
  class ActiveCertificateTests {

    @Test
    void shouldReturnFalseIfCertificateIsInactive() {
      final var actionEvaluation = actionEvaluationBuilder
          .build();

      final var certificate = certificateBuilder
          .certificateModel(
              fk7804certificateModelBuilder()
                  .activeFrom(LocalDateTime.now().plusDays(1))
                  .build()
          )
          .build();

      assertFalse(
          certificateActionCreateDraftFromCertificate.evaluate(Optional.of(certificate),
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
              fk7804certificateModelBuilder()
                  .activeFrom(LocalDateTime.now().minusDays(1))
                  .build()
          )
          .build();

      assertTrue(
          certificateActionCreateDraftFromCertificate.evaluate(Optional.of(certificate),
              Optional.of(actionEvaluation)),
          () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
      );
    }
  }
}