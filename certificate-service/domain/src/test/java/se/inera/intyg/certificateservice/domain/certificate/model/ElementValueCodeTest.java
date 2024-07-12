package se.inera.intyg.certificateservice.domain.certificate.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ElementValueCodeTest {

  @Nested
  class IsEmpty {

    @Test
    void shouldReturnTrueIfNull() {
      assertTrue(
          ElementValueCode.builder().build().isEmpty()
      );
    }

    @Test
    void shouldReturnFalseIfCode() {
      assertFalse(
          ElementValueCode.builder().code("Code 1").build().isEmpty()
      );
    }

    @Test
    void shouldReturnTrueIfEmpty() {
      assertTrue(
          ElementValueCode.builder().code("").build().isEmpty()
      );
    }

    @Test
    void shouldReturnTrueIfBlank() {
      assertTrue(
          ElementValueCode.builder().code("  ").build().isEmpty()
      );
    }
  }
}