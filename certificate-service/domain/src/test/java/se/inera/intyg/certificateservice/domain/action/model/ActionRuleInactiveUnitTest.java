package se.inera.intyg.certificateservice.domain.action.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.unit.model.Inactive;
import se.inera.intyg.certificateservice.domain.unit.model.SubUnit;

class ActionRuleInactiveUnitTest {


  private ActionRuleInactiveUnit actionRuleInactiveUnit;

  @BeforeEach
  void setUp() {
    actionRuleInactiveUnit = new ActionRuleInactiveUnit();
  }

  @Test
  void shallReturnFalseIfUnitIsInactive() {
    final var actionEvaluation = ActionEvaluation.builder()
        .subUnit(SubUnit.builder()
            .inactive(new Inactive(true))
            .build())
        .build();

    final var actualResult = actionRuleInactiveUnit.evaluate(actionEvaluation);

    assertFalse(actualResult);
  }

  @Test
  void shallReturnTrueIfUnitIsNotInactive() {
    final var actionEvaluation = ActionEvaluation.builder()
        .subUnit(SubUnit.builder()
            .inactive(new Inactive(false))
            .build())
        .build();

    final var actualResult = actionRuleInactiveUnit.evaluate(actionEvaluation);

    assertTrue(actualResult);
  }

}