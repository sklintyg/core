package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class ElementConfigurationCheckboxDateRangeListTest {

  private static final String DATE_RANGE_ONE = "DATE_RANGE_ONE";
  private static final String DATE_RANGE_LABE_ONE = "DATE_RANGE_LABE_ONE";
  private static final String DATE_RANGE_TWO = "DATE_RANGE_TWO";
  private static final String DATE_RANGE_LABE_TWO = "DATE_RANGE_LABE_TWO";

  @Test
  void shallReturnDateRangeIfExists() {
    final var expectedDateRange = new ElementConfigurationCode(
        new FieldId(DATE_RANGE_TWO), DATE_RANGE_LABE_TWO);

    final var checkboxDateRangeList = ElementConfigurationCheckboxDateRangeList.builder()
        .dateRanges(
            List.of(
                new ElementConfigurationCode(new FieldId(DATE_RANGE_ONE), DATE_RANGE_LABE_ONE),
                expectedDateRange
            )
        )
        .build();

    assertEquals(expectedDateRange,
        checkboxDateRangeList.checkboxDateRange(new FieldId(DATE_RANGE_TWO)).orElseThrow());
  }

  @Test
  void shallReturnEmptyIfRangeNotExists() {
    final var checkboxDateRangeList = ElementConfigurationCheckboxDateRangeList.builder()
        .dateRanges(
            List.of(
                new ElementConfigurationCode(new FieldId(DATE_RANGE_ONE), DATE_RANGE_LABE_ONE)
            )
        )
        .build();

    assertTrue(checkboxDateRangeList.checkboxDateRange(new FieldId(DATE_RANGE_TWO)).isEmpty());
  }

  @Test
  void shallReturnEmptyIfRangesAreNull() {
    final var checkboxDateRangeList = ElementConfigurationCheckboxDateRangeList.builder()
        .build();

    assertTrue(checkboxDateRangeList.checkboxDateRange(new FieldId(DATE_RANGE_TWO)).isEmpty());
  }
}