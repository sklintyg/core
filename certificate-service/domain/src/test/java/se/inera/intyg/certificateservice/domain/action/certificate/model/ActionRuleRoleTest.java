package se.inera.intyg.certificateservice.domain.action.certificate.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionRuleRole;
import se.inera.intyg.certificateservice.domain.common.model.Role;
import se.inera.intyg.certificateservice.domain.user.model.User;

class ActionRuleRoleTest {

  private ActionRuleRole actionRuleRole;

  @Test
  void shallReturnTrueIfRoleIsPresent() {
    actionRuleRole = new ActionRuleRole(List.of(Role.DOCTOR));
    final var actionEvaluation = ActionEvaluation.builder()
        .user(
            User.builder()
                .role(Role.DOCTOR)
                .build()
        )
        .build();
    assertTrue(actionRuleRole.evaluate(Optional.empty(), Optional.of(actionEvaluation)));
  }

  @Test
  void shallReturnFalseIfRoleIsNotPresent() {
    actionRuleRole = new ActionRuleRole(List.of(Role.CARE_ADMIN, Role.DOCTOR));
    final var actionEvaluation = ActionEvaluation.builder()
        .user(
            User.builder()
                .role(Role.NURSE)
                .build()
        )
        .build();
    assertFalse(actionRuleRole.evaluate(Optional.empty(), Optional.of(actionEvaluation)));
  }
}
