package se.inera.intyg.certificateservice.domain.certificate.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ElementValueDiagnosisTest {

  @Nested
  class IsEmpty {

    @Test
    void shouldReturnTrueIfNull() {
      assertTrue(
          ElementValueDiagnosis.builder()
              .build()
              .isEmpty()
      );
    }

    @Test
    void shouldReturnFalseIfDiagnosisIsDefined() {
      assertFalse(
          ElementValueDiagnosis.builder()
              .code("A20")
              .description("Description")
              .build()
              .isEmpty()
      );
    }

    @Test
    void shouldReturnTrueIfOneValueIsEmpty() {
      assertTrue(
          ElementValueDiagnosis.builder()
              .code("A20")
              .build()
              .isEmpty()
      );
    }
  }

}