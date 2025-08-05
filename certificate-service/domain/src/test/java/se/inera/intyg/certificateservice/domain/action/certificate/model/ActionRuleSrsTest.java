package se.inera.intyg.certificateservice.domain.action.certificate.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.AJLA_DOKTOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.DAN_DENTIST;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.user.model.SrsActive;
import se.inera.intyg.certificateservice.domain.user.model.User;

class ActionRuleSrsTest {

  private ActionRuleSrs actionRuleSrs;

  @Test
  void shallReturnTrueIfSrsIsActiveForUser() {
    actionRuleSrs = new ActionRuleSrs();
    final var actionEvaluation = ActionEvaluation.builder()
        .user(AJLA_DOKTOR)
        .build();

    final var result = actionRuleSrs.evaluate(Optional.empty(), Optional.of(actionEvaluation));
    assertTrue(result);
  }

  @Test
  void shallReturnFalseIfSrsIsNotActiveForUser() {
    actionRuleSrs = new ActionRuleSrs();
    final var actionEvaluation = ActionEvaluation.builder()
        .user(DAN_DENTIST)
        .build();

    final var result = actionRuleSrs.evaluate(Optional.empty(), Optional.of(actionEvaluation));
    assertFalse(result);
  }

  @Test
  void shallReturnFalseIfSrsActiveIsSetToNullForUser() {
    actionRuleSrs = new ActionRuleSrs();
    final var actionEvaluation = ActionEvaluation.builder()
        .user(
            User.builder()
                .srsActive(new SrsActive(null))
                .build()
        )
        .build();

    final var result = actionRuleSrs.evaluate(Optional.empty(), Optional.of(actionEvaluation));
    assertFalse(result);
  }
}