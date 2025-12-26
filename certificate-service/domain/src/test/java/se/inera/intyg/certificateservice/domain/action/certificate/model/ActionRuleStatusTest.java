package se.inera.intyg.certificateservice.domain.action.certificate.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;

class ActionRuleStatusTest {

  @Test
  void shouldReturnCustomGetReasonForPermissionDenied() {
    final var expected =
        "För att genomföra den begärda åtgärden behöver intygets status vara [DRAFT]";
    final var reasonForPermissionDenied = new ActionRuleStatus(
        List.of(Status.DRAFT)).getReasonForPermissionDenied();
    assertEquals(expected, reasonForPermissionDenied);
  }
}