package se.inera.intyg.certificateservice.domain.validation.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ValidationResultTest {

  @Nested
  class IsValid {

    @Test
    void shallReturnTrueIfErrorsIsEmpty() {
      final var validationResult = ValidationResult.builder()
          .build();
      assertTrue(validationResult.isValid());
    }


    @Test
    void shallReturnFalseIfErrorsHasValue() {
      final var validationResult = ValidationResult.builder()
          .errors(List.of(ValidationError.builder().build()))
          .build();
      assertFalse(validationResult.isValid());
    }
  }

  @Nested
  class IsInvalid {

    @Test
    void shallReturnTrueIfErrorsHasValue() {
      final var validationResult = ValidationResult.builder()
          .errors(List.of(ValidationError.builder().build()))
          .build();
      assertTrue(validationResult.isInvalid());
    }


    @Test
    void shallReturnFalseIfErrorsIsEmpty() {
      final var validationResult = ValidationResult.builder()
          .build();
      assertFalse(validationResult.isInvalid());
    }
  }
}
