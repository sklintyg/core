package se.inera.intyg.certificateservice.domain.action.certificate.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.common.model.Agreement;
import se.inera.intyg.certificateservice.domain.user.model.User;

class ActionRuleUserAgreementTest {

  private ActionRuleUserAgreement actionRuleUserAgreement;

  @BeforeEach
  void setUp() {
    actionRuleUserAgreement = new ActionRuleUserAgreement();
  }

  @Test
  void shallReturnFalseIfAgreementIsFalse() {
    final var actionEvaluation = ActionEvaluation.builder()
        .user(
            User.builder()
                .agreement(new Agreement(false))
                .build()
        )
        .build();

    final var actualResult = actionRuleUserAgreement.evaluate(Optional.empty(),
        Optional.of(actionEvaluation));

    assertFalse(actualResult);
  }

  @Test
  void shallReturnTrueIfAgreementIsTrue() {
    final var actionEvaluation = ActionEvaluation.builder()
        .user(
            User.builder()
                .agreement(new Agreement(true))
                .build()
        )
        .build();

    final var actualResult = actionRuleUserAgreement.evaluate(Optional.empty(),
        Optional.of(actionEvaluation));

    assertTrue(actualResult);
  }
}