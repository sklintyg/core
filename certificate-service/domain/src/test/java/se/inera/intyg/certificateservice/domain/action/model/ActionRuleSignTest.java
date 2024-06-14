package se.inera.intyg.certificateservice.domain.action.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataAction.actionEvaluationBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7210CertificateBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.ALF_DOKTOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.ANNA_SJUKSKOTERKSA;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.common.model.Role;

class ActionRuleSignTest {

  private ActionRuleSign actionRuleSign;

  @BeforeEach
  void setUp() {
    actionRuleSign = new ActionRuleSign();
  }

  @Test
  void shallReturnTrueIfUserRoleHasAccessToSign() {
    final var actionEvaluation = actionEvaluationBuilder()
        .user(ALF_DOKTOR)
        .build();

    final var certificateModel = CertificateModel.builder()
        .rolesWithSignAccess(List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR))
        .build();

    final var certificate = fk7210CertificateBuilder()
        .certificateModel(certificateModel)
        .build();

    assertTrue(
        actionRuleSign.evaluate(Optional.of(certificate), Optional.of(actionEvaluation))
    );
  }

  @Test
  void shallReturnFalseIfUserDoesNotHaveAccessToSign() {
    final var actionEvaluation = actionEvaluationBuilder()
        .user(ANNA_SJUKSKOTERKSA)
        .build();

    final var certificateModel = CertificateModel.builder()
        .rolesWithSignAccess(List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR))
        .build();

    final var certificate = fk7210CertificateBuilder()
        .certificateModel(certificateModel)
        .build();

    assertFalse(
        actionRuleSign.evaluate(Optional.of(certificate), Optional.of(actionEvaluation))
    );
  }

}