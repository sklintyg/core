package se.inera.intyg.certificateservice.domain.certificate.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ElementValueDateTest {

  @Nested
  class IsEmpty {

    @Test
    void shouldReturnTrueIfNull() {
      assertTrue(
          ElementValueDate.builder().build().isEmpty()
      );
    }

    @Test
    void shouldReturnFalseIfDate() {
      assertFalse(
          ElementValueDate.builder().date(LocalDate.now()).build().isEmpty()
      );
    }
  }

}