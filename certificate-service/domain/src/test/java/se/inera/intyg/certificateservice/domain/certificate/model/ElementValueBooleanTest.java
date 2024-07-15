package se.inera.intyg.certificateservice.domain.certificate.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ElementValueBooleanTest {

  @Nested
  class IsEmpty {

    @Test
    void shouldReturnTrueIfNull() {
      assertTrue(
          ElementValueBoolean.builder().build().isEmpty()
      );
    }

    @Test
    void shouldReturnFalseIfTrue() {
      assertFalse(
          ElementValueBoolean.builder().value(true).build().isEmpty()
      );
    }

    @Test
    void shouldReturnFalseIfFalse() {
      assertFalse(
          ElementValueBoolean.builder().value(false).build().isEmpty()
      );
    }
  }
}