package se.inera.intyg.certificateservice.domain.action.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
                        .hsaId(new HsaId("subUnitHsaId"))
                        .build()
                )
                .careUnit(
                    CareUnit.builder()
                        .hsaId(new HsaId("careUnitId"))
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
  void shallThrowIfCertificateIsEmpty() {
    assertThrows(IllegalArgumentException.class,
        () -> certificateActionRead.evaluate(Optional.empty(), actionEvaluationBuilder.build()));
  }

  @Test
  void shallReturnTrueIfIssuedUnitMatchesUnit() {
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
}
