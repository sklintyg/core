package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementData.dateElementDataBuilder;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;

class ElementValidationDateTest {

  private ElementValidationDate elementValidationDate;

  @BeforeEach
  void setUp() {
    elementValidationDate = ElementValidationDate.builder()
        .mandatory(true)
        .min(Period.ofDays(0))
        .max(Period.ofDays(30))
        .build();
  }

  @Test
  void shallReturnFalseIfDataIsNull() {
    assertFalse(elementValidationDate.validate(null),
        () -> "Expected value '%s' to be false with validator '%s'".formatted(null,
            elementValidationDate)
    );
  }

  @Test
  void shallReturnFalseIfValueIsNull() {
    final var notDate = dateElementDataBuilder()
        .value(null)
        .build();

    assertFalse(elementValidationDate.validate(notDate),
        () -> "Expected value '%s' to be false with validator '%s'".formatted(notDate,
            elementValidationDate)
    );
  }

  @Test
  void shallReturnTrueIfDateIsNullAndNotMandatory() {
    final var elementValidationDate = ElementValidationDate.builder()
        .mandatory(false)
        .build();
    
    final var notDate = dateElementDataBuilder()
        .value(
            ElementValueDate.builder().build()
        )
        .build();

    assertTrue(elementValidationDate.validate(notDate),
        () -> "Expected value '%s' to be true with validator '%s'".formatted(notDate,
            elementValidationDate)
    );
  }

  @Test
  void shallReturnFalseIfValueIsNotDate() {
    final var notDate = dateElementDataBuilder()
        .value(
            new ElementValue() {
            }
        )
        .build();

    assertFalse(elementValidationDate.validate(notDate),
        () -> "Expected value '%s' to be false with validator '%s'".formatted(notDate,
            elementValidationDate)
    );
  }

  @Test
  void shallReturnTrueIfEqualMin() {
    final var date = dateElementDataBuilder()
        .value(
            ElementValueDate.builder()
                .date(
                    LocalDate.now(ZoneId.systemDefault())
                )
                .build()
        )
        .build();

    assertTrue(elementValidationDate.validate(date),
        () -> "Expected value '%s' to be true with validator '%s'".formatted(date,
            elementValidationDate)
    );
  }

  @Test
  void shallReturnTrueIfBeforeMin() {
    final var date = dateElementDataBuilder()
        .value(
            ElementValueDate.builder()
                .date(
                    LocalDate.now(ZoneId.systemDefault()).minusDays(1)
                )
                .build()
        )
        .build();

    assertFalse(elementValidationDate.validate(date),
        () -> "Expected value '%s' to be false with validator '%s'".formatted(date,
            elementValidationDate)
    );
  }

  @Test
  void shallReturnTrueIfEqualMax() {
    final var date = dateElementDataBuilder()
        .value(
            ElementValueDate.builder()
                .date(
                    LocalDate.now(ZoneId.systemDefault()).plusDays(30)
                )
                .build()
        )
        .build();

    assertTrue(elementValidationDate.validate(date),
        () -> "Expected value '%s' to be true with validator '%s'".formatted(date,
            elementValidationDate)
    );
  }

  @Test
  void shallReturnFalseIfAfterMax() {
    final var date = dateElementDataBuilder()
        .value(
            ElementValueDate.builder()
                .date(
                    LocalDate.now(ZoneId.systemDefault()).plusDays(31)
                )
                .build()
        )
        .build();

    assertFalse(elementValidationDate.validate(date),
        () -> "Expected value '%s' to be false with validator '%s'".formatted(date,
            elementValidationDate)
    );
  }
}