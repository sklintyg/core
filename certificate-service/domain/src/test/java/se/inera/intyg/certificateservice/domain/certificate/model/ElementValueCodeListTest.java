package se.inera.intyg.certificateservice.domain.certificate.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ElementValueCodeListTest {

  @Nested
  class IsEmpty {

    @Test
    void shouldReturnTrueIfNull() {
      assertTrue(
          ElementValueCodeList.builder().build().isEmpty()
      );
    }

    @Test
    void shouldReturnFalseIfCodeListWithValue() {
      assertFalse(
          ElementValueCodeList.builder()
              .list(
                  List.of(ElementValueCode
                      .builder()
                      .code("CodeList 1")
                      .build()
                  )
              )
              .build()
              .isEmpty()
      );
    }

    @Test
    void shouldReturnFalseIfCodeListWithValueAndEmptyValue() {
      assertFalse(
          ElementValueCodeList.builder()
              .list(
                  List.of(ElementValueCode
                          .builder()
                          .code("CodeList 1")
                          .build(),
                      ElementValueCode.builder()
                          .build()
                  )
              )
              .build()
              .isEmpty()
      );
    }

    @Test
    void shouldReturnTrueIfCodeListWithEmptyValue() {
      assertTrue(
          ElementValueCodeList.builder()
              .list(
                  List.of(ElementValueCode.builder()
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
          ElementValueCodeList.builder()
              .list(
                  Collections.emptyList()
              )
              .build()
              .isEmpty()
      );
    }
  }
}