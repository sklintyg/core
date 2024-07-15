package se.inera.intyg.certificateservice.domain.certificate.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ElementValueDateListTest {

  @Nested
  class IsEmpty {

    @Test
    void shouldReturnTrueIfNull() {
      assertTrue(
          ElementValueDateList.builder().build().isEmpty()
      );
    }

    @Test
    void shouldReturnFalseIfDateListWithValue() {
      assertFalse(
          ElementValueDateList.builder()
              .dateList(
                  List.of(ElementValueDate
                      .builder()
                      .date(LocalDate.now())
                      .build()
                  )
              )
              .build()
              .isEmpty()
      );
    }

    @Test
    void shouldReturnFalseIfDateListWithValueAndEmptyValue() {
      assertFalse(
          ElementValueDateList.builder()
              .dateList(
                  List.of(ElementValueDate
                          .builder()
                          .date(LocalDate.now())
                          .build(),
                      ElementValueDate.builder()
                          .build()
                  )
              )
              .build()
              .isEmpty()
      );
    }

    @Test
    void shouldReturnTrueIfDateListWithEmptyValue() {
      assertTrue(
          ElementValueDateList.builder()
              .dateList(
                  List.of(ElementValueDate.builder()
                      .build()
                  )
              )
              .build()
              .isEmpty()
      );
    }

    @Test
    void shouldReturnTrueIfEmpty() {
      assertTrue(
          ElementValueDateList.builder()
              .dateList(
                  Collections.emptyList()
              )
              .build()
              .isEmpty()
      );
    }
  }
}