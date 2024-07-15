package se.inera.intyg.certificateservice.domain.action.certificate.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.common.model.AccessScope;
import se.inera.intyg.certificateservice.domain.user.model.User;

class ActionRuleUserHasAccessScopeTest {

  @Test
  void shallReturnTrueIfScopesContainsUserAccessScope() {
    final var actionRuleUserHasAccessScope = new ActionRuleUserHasAccessScope(
        List.of(AccessScope.WITHIN_CARE_UNIT)
    );

    final var actionEvaluation = ActionEvaluation.builder()
        .user(
            User.builder()
                .accessScope(AccessScope.WITHIN_CARE_UNIT)
                .build()
        )
        .build();

    final var result = actionRuleUserHasAccessScope.evaluate(Optional.empty(),
        Optional.of(actionEvaluation));
    assertTrue(result);
  }

  @Test
  void shallReturnFalseIfScopesDontContainUserAccessScope() {
    final var actionRuleUserHasAccessScope = new ActionRuleUserHasAccessScope(
        List.of(AccessScope.WITHIN_CARE_UNIT)
    );

    final var actionEvaluation = ActionEvaluation.builder()
        .user(
            User.builder()
                .accessScope(AccessScope.WITHIN_CARE_PROVIDER)
                .build()
        )
        .build();

    final var result = actionRuleUserHasAccessScope.evaluate(Optional.empty(),
        Optional.of(actionEvaluation));
    assertFalse(result);
  }
}
