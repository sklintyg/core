package se.inera.intyg.certificateservice.domain.action.certificate.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionRuleUserAllowCopy;
import se.inera.intyg.certificateservice.domain.common.model.AllowCopy;
import se.inera.intyg.certificateservice.domain.user.model.User;

class ActionRuleUserAllowCopyTest {

  private ActionRuleUserAllowCopy actionRuleUserAllowCopy;

  @BeforeEach
  void setUp() {
    actionRuleUserAllowCopy = new ActionRuleUserAllowCopy();
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

    final var actualResult = actionRuleUserAllowCopy.evaluate(Optional.empty(),
        Optional.of(actionEvaluation));

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

    final var actualResult = actionRuleUserAllowCopy.evaluate(Optional.empty(),
        Optional.of(actionEvaluation));

    assertTrue(actualResult);
  }
}