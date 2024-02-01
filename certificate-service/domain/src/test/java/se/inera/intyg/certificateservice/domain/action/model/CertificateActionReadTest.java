package se.inera.intyg.certificateservice.domain.action.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.Blocked;
import se.inera.intyg.certificateservice.domain.certificate.model.CareProvider;
import se.inera.intyg.certificateservice.domain.certificate.model.CareUnit;
import se.inera.intyg.certificateservice.domain.certificate.model.Inactive;
import se.inera.intyg.certificateservice.domain.certificate.model.SubUnit;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;
import se.inera.intyg.certificateservice.domain.user.model.User;

class CertificateActionReadTest {

  private CertificateActionRead certificateActionRead;
  private ActionEvaluation.ActionEvaluationBuilder actionEvaluationBuilder;

  @BeforeEach
  void setUp() {
    certificateActionRead = new CertificateActionRead(
        CertificateActionSpecification.builder()
            .certificateActionType(CertificateActionType.READ)
            .build()
    );
    actionEvaluationBuilder = ActionEvaluation.builder()
        .user(
            User.builder()
                .blocked(new Blocked(false))
                .build()
        )
        .subUnit(
            SubUnit.builder()
                .inactive(new Inactive(false))
                .build()
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
  void shallReturnFalseIfUserIsBlocked() {
    actionEvaluationBuilder
        .user(
            User.builder()
                .blocked(new Blocked(true))
                .build()
        );
    final var result = certificateActionRead.evaluate(actionEvaluationBuilder.build());

    assertFalse(result);
  }

  @Test
  void shallReturnFalseIfIssuingUnitIsBlocked() {
    actionEvaluationBuilder
        .subUnit(
            SubUnit.builder()
                .inactive(new Inactive(true))
                .build()
        )
        .build();

    final var result = certificateActionRead.evaluate(actionEvaluationBuilder.build());

    assertFalse(result);
  }

  @Test
  void shallReturnFalseIfUserIsBlockedAndIssuingUnitIsBlocked() {
    actionEvaluationBuilder
        .subUnit(
            SubUnit.builder()
                .inactive(new Inactive(true))
                .build()
        )
        .user(
            User.builder()
                .blocked(new Blocked(true))
                .build()
        )
        .build();

    final var result = certificateActionRead.evaluate(actionEvaluationBuilder.build());

    assertFalse(result);
  }
}
