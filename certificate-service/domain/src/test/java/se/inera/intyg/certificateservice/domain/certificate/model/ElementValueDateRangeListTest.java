package se.inera.intyg.certificateservice.domain.certificate.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class ElementValueDateRangeListTest {


  private static final LocalDate LAST_TO_DATE = LocalDate.now(ZoneId.systemDefault());

  @Test
  void shallReturnLastDateRangeWhenOnlyOne() {
    final var expectedDateRange = DateRange.builder()
        .dateRangeId(new FieldId("last"))
        .from(LAST_TO_DATE.minusDays(10))
        .to(LAST_TO_DATE)
        .build();

    final var dateRangeList = ElementValueDateRangeList.builder()
        .dateRangeList(
            List.of(
                expectedDateRange
            )
        )
        .build();

    assertEquals(expectedDateRange, dateRangeList.lastRange());
  }

  @Test
  void shallReturnLastDateRangeWhenMultiple() {
    final var expectedDateRange = DateRange.builder()
        .dateRangeId(new FieldId("last"))
        .from(LAST_TO_DATE.minusDays(10))
        .to(LAST_TO_DATE)
        .build();

    final var dateRangeList = ElementValueDateRangeList.builder()
        .dateRangeList(
            List.of(
                DateRange.builder()
                    .dateRangeId(new FieldId("first"))
                    .from(LAST_TO_DATE.minusDays(40))
                    .to(LAST_TO_DATE.minusDays(31))
                    .build(),
                DateRange.builder()
                    .dateRangeId(new FieldId("middle"))
                    .from(LAST_TO_DATE.minusDays(30))
                    .to(LAST_TO_DATE.minusDays(21))
                    .build(),
                expectedDateRange
            )
        )
        .build();

    assertEquals(expectedDateRange, dateRangeList.lastRange());
  }

  @Test
  void shallReturnNullIfDateRangeListIsNull() {
    final var dateRangeList = ElementValueDateRangeList.builder()
        .build();

    assertNull(dateRangeList.lastRange());
  }

  @Test
  void shallReturnNullIfDateRangeListIsEmpty() {
    final var dateRangeList = ElementValueDateRangeList.builder()
        .build();

    assertNull(dateRangeList.lastRange());
  }

  @Nested
  class IsEmpty {

    @Test
    void shouldReturnTrueIfNull() {
      assertTrue(
          ElementValueDateRangeList.builder().build().isEmpty()
      );
    }

    @Test
    void shouldReturnFalseIfDateRangeListWithNonEmptyValue() {
      assertFalse(
          ElementValueDateRangeList.builder()
              .dateRangeList(
                  List.of(DateRange
                      .builder()
                      .to(LocalDate.now())
                      .from(LocalDate.now())
                      .build()
                  )
              )
              .build()
              .isEmpty()
      );
    }

    @Test
    void shouldReturnTrueIfDateRangeListWithEmptyValue() {
      assertTrue(
          ElementValueDateRangeList.builder()
              .dateRangeList(
                  List.of(DateRange
                      .builder()
                      .from(LocalDate.now())
                      .build()
                  )
              )
              .build()
              .isEmpty()
      );
    }

    @Test
    void shouldReturnFalseIfDateRangeListWithEmptyValueAndFilledValue() {
      assertFalse(
          ElementValueDateRangeList.builder()
              .dateRangeList(
                  List.of(DateRange
                          .builder()
                          .to(LocalDate.now())
                          .from(LocalDate.now())
                          .build(),
                      DateRange.builder()
                          .build()
                  )
              )
              .build()
              .isEmpty()
      );
    }

    @Test
    void shouldReturnFalseIfEmpty() {
      assertTrue(
          ElementValueDateRangeList.builder()
              .dateRangeList(
                  Collections.emptyList()
              )
              .build()
              .isEmpty()
      );
    }
  }
}