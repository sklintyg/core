package se.inera.intyg.certificateservice.domain.action.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.CareProvider;
import se.inera.intyg.certificateservice.domain.certificate.model.CareUnit;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate.CertificateBuilder;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.certificate.model.HsaId;
import se.inera.intyg.certificateservice.domain.certificate.model.SubUnit;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;
import se.inera.intyg.certificateservice.domain.user.model.User;

class CertificateActionReadTest {

  private static final String SUB_UNIT_HSA_ID = "subUnitHsaId";
  private static final String CARE_UNIT_HSA_ID = "careUnitId";
  private static final String OTHER_SUB_UNIT = "otherSubUnit";
  private static final String OTHER_CARE_UNIT = "otherCareUnit";
  private CertificateActionRead certificateActionRead;
  private ActionEvaluation.ActionEvaluationBuilder actionEvaluationBuilder;
  private CertificateBuilder certificateBuilder;

  @BeforeEach
  void setUp() {
    certificateActionRead = new CertificateActionRead(
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.READ)
            .build()
    );
    certificateBuilder = Certificate.builder()
        .certificateMetaData(
            CertificateMetaData.builder()
                .issuingUnit(
                    SubUnit.builder()
                        .hsaId(new HsaId(SUB_UNIT_HSA_ID))
                        .build()
                )
                .careUnit(
                    CareUnit.builder()
                        .hsaId(new HsaId(CARE_UNIT_HSA_ID))
                        .build()
                )
                .careProvider(
                    CareProvider.builder()
                        .hsaId(new HsaId("careProviderId"))
                        .build()
                )
                .build()
        );
    actionEvaluationBuilder = ActionEvaluation.builder()
        .user(
            User.builder().build()
        )
        .subUnit(
            SubUnit.builder().build()
        )
        .patient(
            Patient.builder().build()
        )
        .careProvider(
            CareProvider.builder().build()
        )
        .careUnit(
            CareUnit.builder().build()
        );
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
        certificateActionRead.evaluate(certificate, actionEvaluation),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnTrueIfIssuedUnitMatchesSubUnit() {
    final var actionEvaluation = actionEvaluationBuilder
        .subUnit(
            SubUnit.builder()
                .hsaId(new HsaId(SUB_UNIT_HSA_ID))
                .build()
        )
        .build();

    final var certificate = certificateBuilder.build();

    assertTrue(
        certificateActionRead.evaluate(Optional.of(certificate), actionEvaluation),
        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnTrueIfCareUnitMatchesSubUnit() {
    final var actionEvaluation = actionEvaluationBuilder
        .subUnit(
            SubUnit.builder()
                .hsaId(new HsaId(CARE_UNIT_HSA_ID))
                .build()
        )
        .build();

    final var certificate = certificateBuilder.build();

    assertTrue(
        certificateActionRead.evaluate(Optional.of(certificate), actionEvaluation),
        () -> "Expected true when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnFalseIfIssuedUnitDontMatchSubUnit() {
    final var actionEvaluation = actionEvaluationBuilder
        .subUnit(
            SubUnit.builder()
                .hsaId(new HsaId(OTHER_SUB_UNIT))
                .build()
        )
        .build();

    final var certificate = certificateBuilder.build();

    assertFalse(
        certificateActionRead.evaluate(Optional.of(certificate), actionEvaluation),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }

  @Test
  void shallReturnFalseIfCareUnitDontMatchSubUnitAndSubUnitDontMatchIssuingUnit() {
    final var actionEvaluation = actionEvaluationBuilder
        .subUnit(
            SubUnit.builder()
                .hsaId(new HsaId(OTHER_CARE_UNIT))
                .build()
        )
        .build();

    final var certificate = certificateBuilder.build();

    assertFalse(
        certificateActionRead.evaluate(Optional.of(certificate), actionEvaluation),
        () -> "Expected false when passing %s and %s".formatted(actionEvaluation, certificate)
    );
  }
}
