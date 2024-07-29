package se.inera.intyg.certificateservice.domain.certificate.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ElementValueTextTest {

  @Nested
  class IsEmpty {

    @Test
    void shouldReturnTrueIfNull() {
      assertTrue(
          ElementValueText.builder().build().isEmpty()
      );
    }

    @Test
    void shouldReturnFalseIfText() {
      assertFalse(
          ElementValueText.builder().text("Text 1").build().isEmpty()
      );
    }

    @Test
    void shouldReturnTrueIfEmpty() {
      assertTrue(
          ElementValueText.builder().text("").build().isEmpty()
      );
    }

    @Test
    void shouldReturnTrueIfBlank() {
      assertTrue(
          ElementValueText.builder().text("  ").build().isEmpty()
      );
    }
  }
}