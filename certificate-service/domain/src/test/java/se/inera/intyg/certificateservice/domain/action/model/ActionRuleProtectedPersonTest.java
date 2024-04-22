package se.inera.intyg.certificateservice.domain.action.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ActionRuleProtectedPersonTest {

  private ActionRuleProtectedPerson actionRuleProtectedPerson;

  @BeforeEach
  void setUp() {
    actionRuleProtectedPerson = new ActionRuleProtectedPerson();
  }

  @Test
  void shallReturnErrorMessage() {
    assertEquals(
        "Du saknar behörighet för den begärda åtgärden."
            + " Det krävs särskilda rättigheter eller en specifik befattning"
            + " för att hantera patienter med skyddade personuppgifter.",
        actionRuleProtectedPerson.getErrorMessage()
    );
  }
}
