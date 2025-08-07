package se.inera.intyg.certificateservice.domain.validation.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.Period;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ElementValidatorTest {

  @Nested
  class TextLimit {

    @Test
    void shouldReturnTrueIfTextIsOverLimit() {
      assertTrue(ElementValidator.isTextOverLimit("12345", 4));
    }

    @Test
    void shouldReturnFalseIfTextIsNull() {
      assertFalse(ElementValidator.isTextOverLimit(null, 4));
    }

    @Test
    void shouldReturnFalseIfLimitIsNull() {
      assertFalse(ElementValidator.isTextOverLimit("124", null));
    }

    @Test
    void shouldReturnFalseIfTextIsLessThanLimit() {
      assertFalse(ElementValidator.isTextOverLimit("12", 4));
    }
  }

  @Nested
  class IntegerLimit {

    @Test
    void shouldReturnTrueIfIntegerIsWithinLimit() {
      assertTrue(ElementValidator.isIntegerWithinLimit(5, 1, 10));
    }

    @Test
    void shouldReturnFalseIfIntegerIsNull() {
      assertFalse(ElementValidator.isIntegerWithinLimit(null, 1, 10));
    }

    @Test
    void shouldReturnFalseIfIntegerIsBelowMin() {
      assertFalse(ElementValidator.isIntegerWithinLimit(0, 1, 10));
    }

    @Test
    void shouldReturnFalseIfIntegerIsAboveMax() {
      assertFalse(ElementValidator.isIntegerWithinLimit(11, 1, 10));
    }

    @Test
    void shouldReturnTrueIfMinAndMaxAreNull() {
      assertTrue(ElementValidator.isIntegerWithinLimit(5, null, null));
    }

    @Test
    void shouldReturnTrueIfOnlyMinIsNullAndValueIsBelowMax() {
      assertTrue(ElementValidator.isIntegerWithinLimit(5, null, 10));
    }

    @Test
    void shouldReturnFalseIfOnlyMinIsNullAndValueIsAboveMax() {
      assertFalse(ElementValidator.isIntegerWithinLimit(15, null, 10));
    }

    @Test
    void shouldReturnTrueIfOnlyMaxIsNullAndValueIsAboveMin() {
      assertTrue(ElementValidator.isIntegerWithinLimit(15, 10, null));
    }

    @Test
    void shouldReturnFalseIfOnlyMaxIsNullAndValueIsBelowMin() {
      assertFalse(ElementValidator.isIntegerWithinLimit(5, 10, null));
    }


  }

  @Nested
  class TextDefined {

    @Test
    void shouldReturnTrueIfTextIsDefined() {
      assertTrue(ElementValidator.isTextDefined("12345"));
    }

    @Test
    void shouldReturnFalseIfTextIsNull() {
      assertFalse(ElementValidator.isTextDefined(null));
    }

    @Test
    void shouldReturnFalseIfTextIsBlank() {
      assertFalse(ElementValidator.isTextDefined("  "));
    }

    @Test
    void shouldReturnFalseIfTextIsEmpty() {
      assertFalse(ElementValidator.isTextDefined(""));
    }
  }

  @Nested
  class DateAfterMax {

    @Test
    void shouldReturnTrueIfDateIsAfterMaxAndMaxIsNegative() {
      assertTrue(ElementValidator.isDateAfterMax(LocalDate.now(), Period.ofDays(-1)));
    }

    @Test
    void shouldReturnTrueIfDateIsAfterMaxAndMaxIsPositive() {
      assertTrue(ElementValidator.isDateAfterMax(LocalDate.now().plusDays(2), Period.ofDays(1)));
    }

    @Test
    void shouldReturnFalseIfDateIsBeforeMaxAndMaxIsNegative() {
      assertFalse(ElementValidator.isDateAfterMax(LocalDate.now().minusDays(1), Period.ofDays(-1)));
    }

    @Test
    void shouldReturnFalseIfDateIsBeforeMaxAndMaxIsPositive() {
      assertFalse(ElementValidator.isDateAfterMax(LocalDate.now().minusDays(1), Period.ofDays(1)));
    }
  }

  @Nested
  class DateBeforeMin {

    @Test
    void shouldReturnTrueIfDateIsBeforeMinAndMinIsNegative() {
      assertTrue(ElementValidator.isDateBeforeMin(LocalDate.now().minusDays(2), Period.ofDays(-1)));
    }

    @Test
    void shouldReturnTrueIfDateIsBeforeMinAndMinIsPositive() {
      assertTrue(ElementValidator.isDateBeforeMin(LocalDate.now(), Period.ofDays(1)));
    }

    @Test
    void shouldReturnFalseIfDateIsAfterMinAndMinIsNegative() {
      assertFalse(ElementValidator.isDateBeforeMin(LocalDate.now().plusDays(1), Period.ofDays(-1)));
    }

    @Test
    void shouldReturnFalseIfDateIsAfterMinAndMinIsPositive() {
      assertFalse(ElementValidator.isDateBeforeMin(LocalDate.now().plusDays(2), Period.ofDays(1)));
    }
  }

  @Nested
  class ToDateFromTemporalAmount {

    @Test
    void shouldReturnTodaysDateIfZero() {
      assertEquals(LocalDate.now(), ElementValidator.toDateFromTemporalAmount(Period.ofDays(0)));
    }

    @Test
    void shouldReturnYesterdaysDateIfMinusOne() {
      assertEquals(LocalDate.now().minusDays(1),
          ElementValidator.toDateFromTemporalAmount(Period.ofDays(-1)));
    }

    @Test
    void shouldReturnTomorrowsDateIfPlusOneDay() {
      assertEquals(LocalDate.now().plusDays(1),
          ElementValidator.toDateFromTemporalAmount(Period.ofDays(1)));
    }

    @Test
    void shouldReturnDateInOneYear() {
      assertEquals(LocalDate.now().plusYears(1),
          ElementValidator.toDateFromTemporalAmount(Period.ofYears(1)));
    }
  }

}