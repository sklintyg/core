package se.inera.intyg.certificateservice.domain.action.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.common.model.AllowCopy;
import se.inera.intyg.certificateservice.domain.user.model.User;

class ActionRuleAllowCopyTest {


  private ActionRuleAllowCopy actionRuleAllowCopy;

  @BeforeEach
  void setUp() {
    actionRuleAllowCopy = new ActionRuleAllowCopy();
  }

  @Test
  void shallReturnFalseIfAllowCopyIsFalse() {
    final var actionEvaluation = ActionEvaluation.builder()
        .user(
            User.builder()
                .allowCopy(new AllowCopy(false))
                .build()
        )
        .build();

    final var actualResult = actionRuleAllowCopy.evaluate(actionEvaluation);

    assertFalse(actualResult);
  }

  @Test
  void shallReturnTrueIfAllowCopyIsTrue() {
    final var actionEvaluation = ActionEvaluation.builder()
        .user(
            User.builder()
                .allowCopy(new AllowCopy(true))
                .build()
        )
        .build();

    final var actualResult = actionRuleAllowCopy.evaluate(actionEvaluation);

    assertTrue(actualResult);
  }

}