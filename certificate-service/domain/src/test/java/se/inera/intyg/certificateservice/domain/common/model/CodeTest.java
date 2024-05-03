package se.inera.intyg.certificateservice.domain.common.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CodeTest {

  private static final String DISPLAY_NAME = "displayName";

  @Test
  void shallReturnTrueIfCodeSystemAndCodeMatch() {
    final var code = new Code("code", "codeSystem", DISPLAY_NAME);
    assertTrue(code.matches(new Code("code", "codeSystem", DISPLAY_NAME)));
  }

  @Test
  void shallReturnTrueIfCodeSystemMatchAndCodeMatchButDisplayNameDontMatch() {
    final var code = new Code("code", "codeSystem", DISPLAY_NAME);
    assertTrue(code.matches(new Code("code", "codeSystem", "otherDisplayName")));
  }

  @Test
  void shallReturnFalseIfCodeSystemDontMatchAndCodeMatch() {
    final var code = new Code("code", "anotherCodeSystem", DISPLAY_NAME);
    assertFalse(code.matches(new Code("code", "codeSystem", DISPLAY_NAME)));
  }

  @Test
  void shallReturnFalseIfCodeSystemMatchAndCodeDontMatch() {
    final var code = new Code("anotherCode", "codeSystem", DISPLAY_NAME);
    assertFalse(code.matches(new Code("code", "codeSystem", DISPLAY_NAME)));
  }
}
