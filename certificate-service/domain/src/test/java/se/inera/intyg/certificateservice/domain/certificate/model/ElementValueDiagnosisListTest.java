package se.inera.intyg.certificateservice.domain.certificate.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ElementValueDiagnosisListTest {

  @Nested
  class IsEmpty {

    @Test
    void shouldReturnTrueIfNull() {
      assertTrue(
          ElementValueDiagnosisList.builder().build().isEmpty()
      );
    }

    @Test
    void shouldReturnFalseIfDiagnosisListWithValue() {
      assertFalse(
          ElementValueDiagnosisList.builder()
              .diagnoses(
                  List.of(ElementValueDiagnosis
                      .builder()
                      .code("DiagnosisList 1")
                      .description("Description")
                      .build()
                  )
              )
              .build()
              .isEmpty()
      );
    }

    @Test
    void shouldReturnFalseIfDiagnosisListWithValueAndEmptyValue() {
      assertFalse(
          ElementValueDiagnosisList.builder()
              .diagnoses(
                  List.of(ElementValueDiagnosis
                          .builder()
                          .code("DiagnosisList 1")
                          .description("Description")
                          .build(),
                      ElementValueDiagnosis.builder()
                          .build()
                  )
              )
              .build()
              .isEmpty()
      );
    }

    @Test
    void shouldReturnTrueIfDiagnosisListWithEmptyValue() {
      assertTrue(
          ElementValueDiagnosisList.builder()
              .diagnoses(
                  List.of(ElementValueDiagnosis.builder()
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
          ElementValueDiagnosisList.builder()
              .diagnoses(
                  Collections.emptyList()
              )
              .build()
              .isEmpty()
      );
    }
  }
}