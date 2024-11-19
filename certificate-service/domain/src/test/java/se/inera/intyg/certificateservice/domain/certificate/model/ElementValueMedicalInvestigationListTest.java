package se.inera.intyg.certificateservice.domain.certificate.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ElementValueMedicalInvestigationListTest {

  @Nested
  class IsEmpty {

    @Test
    void shouldReturnTrueIfNull() {
      assertTrue(
          ElementValueMedicalInvestigationList.builder().build().isEmpty()
      );
    }

    @Test
    void shouldReturnFalseIfMedicalInvestigationListWithValue() {
      assertFalse(
          ElementValueMedicalInvestigationList.builder()
              .list(
                  List.of(MedicalInvestigation
                      .builder()
                      .investigationType(
                          ElementValueCode.builder()
                              .code("CODE")
                              .build()
                      )
                      .informationSource(
                          ElementValueText.builder()
                              .text("TEXT")
                              .build()
                      )
                      .date(
                          ElementValueDate.builder()
                              .date(LocalDate.now())
                              .build()
                      )
                      .build()
                  )
              )
              .build()
              .isEmpty()
      );
    }

    @Test
    void shouldReturnFalseIfMedicalInvestigationListWithValueAndEmptyValue() {
      assertFalse(
          ElementValueMedicalInvestigationList.builder()
              .list(
                  List.of(MedicalInvestigation
                          .builder()
                          .investigationType(
                              ElementValueCode.builder()
                                  .code("CODE")
                                  .build()
                          )
                          .informationSource(
                              ElementValueText.builder()
                                  .text("TEXT")
                                  .build()
                          )
                          .date(
                              ElementValueDate.builder()
                                  .date(LocalDate.now())
                                  .build()
                          )
                          .build(),
                      MedicalInvestigation.builder().build()
                  )
              )
              .build()
              .isEmpty()
      );
    }

    @Test
    void shouldReturnTrueIfMedicalInvestigationListWithEmptyValue() {
      assertTrue(
          ElementValueMedicalInvestigationList.builder()
              .list(
                  List.of(MedicalInvestigation
                      .builder()
                      .investigationType(ElementValueCode.builder().build())
                      .informationSource(ElementValueText.builder().build())
                      .date(ElementValueDate.builder().build())
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
          ElementValueMedicalInvestigationList.builder()
              .list(
                  Collections.emptyList()
              )
              .build()
              .isEmpty()
      );
    }

    @Test
    void shouldReturnTrueIfOneMissingValue() {
      assertTrue(
          ElementValueMedicalInvestigationList.builder()
              .list(
                  List.of(MedicalInvestigation
                      .builder()
                      .investigationType(
                          ElementValueCode.builder()
                              .code("CODE")
                              .build()
                      )
                      .informationSource(
                          ElementValueText.builder()
                              .text("TEXT")
                              .build()
                      )
                      .date(ElementValueDate.builder().build())
                      .build()
                  )
              )
              .build()
              .isEmpty()
      );
    }
  }
}