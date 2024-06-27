package se.inera.intyg.certificateservice.domain.validation.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueMedicalInvestigationList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueUnitContactInformation;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalInvestigation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class ElementValidationMedicalInvestigationListTest {

  private static final ElementData ELEMENT_DATA = ElementData.builder().build();
  private static final ElementId ELEMENT_ID = new ElementId("elementId");
  private static final FieldId FIELD_ID = new FieldId("MedicalInvestigation");
  private static final FieldId FIELD_ID_MEDICAL_INV_ONE = new FieldId("fieldIdOne");
  private static final FieldId FIELD_ID_MEDICAL_INV_TWO = new FieldId("fieldIdTwo");
  private static final ElementId CATEGORY_ID = new ElementId("categoryId");

  private static final MedicalInvestigation EMPTY_MEDICAL_INV = MedicalInvestigation.builder()
      .id(new FieldId("EMPTY_MI_1"))
      .date(ElementValueDate.builder().build())
      .investigationType(ElementValueCode.builder().build())
      .informationSource(ElementValueText.builder().build())
      .build();

  public static final LocalDate MAX_DATE = LocalDate.now();
  public static final LocalDate DATE = LocalDate.now().plusDays(5);
  private static final MedicalInvestigation COMPLETE_MEDICAL_INV = MedicalInvestigation.builder()
      .id(new FieldId("COMPLETE_MI_1"))
      .date(ElementValueDate.builder()
          .dateId(new FieldId("DATE_1"))
          .date(DATE)
          .build())
      .investigationType(
          ElementValueCode.builder()
              .codeId(new FieldId("CODE_1"))
              .code("CODE")
              .build()
      )
      .informationSource(
          ElementValueText.builder()
              .textId(new FieldId("TEXT_1"))
              .text("TEXT")
              .build())
      .build();

  private static final MedicalInvestigation COMPLETE_MEDICAL_INV_2 = MedicalInvestigation.builder()
      .id(new FieldId("COMPLETE_MI_2"))
      .date(ElementValueDate.builder()
          .dateId(new FieldId("DATE_11"))
          .date(DATE)
          .build())
      .investigationType(
          ElementValueCode.builder()
              .codeId(new FieldId("CODE_11"))
              .code("CODE")
              .build()
      )
      .informationSource(
          ElementValueText.builder()
              .textId(new FieldId("TEXT_11"))
              .text("TEXT")
              .build())
      .build();

  private static final MedicalInvestigation INCOMPLETE_MEDICAL_INV_DATE = MedicalInvestigation.builder()
      .id(new FieldId("INCOMPLETE_MI_1"))
      .date(ElementValueDate.builder()
          .dateId(new FieldId("DATE_1"))
          .build())
      .investigationType(
          ElementValueCode.builder()
              .codeId(new FieldId("CODE_1"))
              .code("CODE")
              .build()
      )
      .informationSource(
          ElementValueText.builder()
              .textId(new FieldId("TEXT_1"))
              .text("TEXT")
              .build())
      .build();

  private static final MedicalInvestigation INCOMPLETE_MEDICAL_INV_TEXT = MedicalInvestigation.builder()
      .id(new FieldId("INCOMPLETE_MI_2"))
      .date(ElementValueDate.builder()
          .dateId(new FieldId("DATE_2"))
          .date(DATE)
          .build())
      .investigationType(
          ElementValueCode.builder()
              .codeId(new FieldId("CODE_2"))
              .code("CODE")
              .build()
      )
      .informationSource(
          ElementValueText.builder()
              .textId(new FieldId("TEXT_2"))
              .build())
      .build();

  private static final MedicalInvestigation INCOMPLETE_MEDICAL_INV_CODE = MedicalInvestigation.builder()
      .id(new FieldId("INCOMPLETE_MI_3"))
      .date(ElementValueDate.builder()
          .dateId(new FieldId("DATE_3"))
          .date(DATE)
          .build())
      .investigationType(
          ElementValueCode.builder()
              .codeId(new FieldId("CODE_3"))
              .build()
      )
      .informationSource(
          ElementValueText.builder()
              .textId(new FieldId("TEXT_3"))
              .text("TEXT")
              .build())
      .build();

  private ElementValidationMedicalInvestigationList validation;

  @Nested
  class IllegalStates {

    @BeforeEach
    void setUp() {
      validation = ElementValidationMedicalInvestigationList.builder().build();
    }

    @Test
    void shallThrowIllegalArgumentExceptionIfDataIsNull() {
      final Optional<ElementId> categoryId = Optional.empty();
      assertThrows(IllegalArgumentException.class,
          () -> validation.validate(null, categoryId)
      );
    }

    @Test
    void shallThrowIllegalArgumentExceptionIfValueIsNull() {
      final Optional<ElementId> categoryId = Optional.empty();
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .build();

      assertThrows(IllegalArgumentException.class,
          () -> validation.validate(elementData, categoryId));
    }

    @Test
    void shallThrowIllegalArgumentExceptionIfValueIsWrongType() {
      final Optional<ElementId> categoryId = Optional.empty();
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(ElementValueUnitContactInformation.builder().build())
          .build();

      assertThrows(IllegalArgumentException.class,
          () -> validation.validate(elementData, categoryId));
    }
  }

  @Nested
  class DateAfterMaxErrors {

    @Test
    void shallNotReturnErrorMessageIfNoMax() {
      validation = ElementValidationMedicalInvestigationList.builder().build();
      final var categoryId = Optional.of(CATEGORY_ID);
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueMedicalInvestigationList.builder()
                  .id(FIELD_ID)
                  .list(
                      List.of(
                          COMPLETE_MEDICAL_INV
                      )
                  )
                  .build()
          )
          .build();

      final var validationErrors = validation.validate(elementData, categoryId);
      assertEquals(Collections.emptyList(), validationErrors);
    }

    @Test
    void shallReturnErrorMessageIfDateIsAfterMax() {
      validation = ElementValidationMedicalInvestigationList.builder()
          .max(Period.ofDays(0))
          .build();
      final var expectedValidationError = getExpectedValidationError(
          "Ange ett datum som är senast " + MAX_DATE + ".",
          COMPLETE_MEDICAL_INV.date().dateId());
      final var categoryId = Optional.of(CATEGORY_ID);
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueMedicalInvestigationList.builder()
                  .id(FIELD_ID)
                  .list(
                      List.of(
                          COMPLETE_MEDICAL_INV
                      )
                  )
                  .build()
          )
          .build();

      final var validationErrors = validation.validate(elementData, categoryId);
      assertTrue(validationErrors.contains(expectedValidationError.get(0)));
    }

    @Test
    void shallReturnMultipleErrorMessageIfDatesAreAfterMaxAndMaxIsSet() {
      validation = ElementValidationMedicalInvestigationList.builder()
          .max(Period.ofDays(0))
          .build();
      final var expectedValidationError = Stream.concat(getExpectedValidationError(
              "Ange ett datum som är senast " + MAX_DATE + ".",
              COMPLETE_MEDICAL_INV.date().dateId()
          ).stream(), getExpectedValidationError(
              "Ange ett datum som är senast " + MAX_DATE + ".",
              COMPLETE_MEDICAL_INV_2.date().dateId()
          ).stream()
      ).toList();

      final var categoryId = Optional.of(CATEGORY_ID);
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueMedicalInvestigationList.builder()
                  .id(FIELD_ID)
                  .list(
                      List.of(
                          COMPLETE_MEDICAL_INV, COMPLETE_MEDICAL_INV_2
                      )
                  )
                  .build()
          )
          .build();

      final var validationErrors = validation.validate(elementData, categoryId);
      assertTrue(validationErrors.contains(expectedValidationError.get(0)));
      assertTrue(validationErrors.contains(expectedValidationError.get(1)));
    }
  }

  @Nested
  class DateBeforeMinErrors {

    @Test
    void shallNotReturnErrorMessageIfNoMin() {
      validation = ElementValidationMedicalInvestigationList.builder().build();
      final var categoryId = Optional.of(CATEGORY_ID);
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueMedicalInvestigationList.builder()
                  .id(FIELD_ID)
                  .list(
                      List.of(
                          COMPLETE_MEDICAL_INV
                      )
                  )
                  .build()
          )
          .build();

      final var validationErrors = validation.validate(elementData, categoryId);
      assertEquals(Collections.emptyList(), validationErrors);
    }

    @Test
    void shallReturnErrorMessageIfDateIsBeforeMin() {
      validation = ElementValidationMedicalInvestigationList.builder()
          .min(Period.ofDays(10))
          .build();
      final var expectedValidationError = getExpectedValidationError(
          "Ange ett datum som är tidigast " + LocalDate.now().plusDays(10) + ".",
          COMPLETE_MEDICAL_INV.date().dateId());
      final var categoryId = Optional.of(CATEGORY_ID);
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueMedicalInvestigationList.builder()
                  .id(FIELD_ID)
                  .list(
                      List.of(
                          COMPLETE_MEDICAL_INV
                      )
                  )
                  .build()
          )
          .build();

      final var validationErrors = validation.validate(elementData, categoryId);
      assertTrue(validationErrors.contains(expectedValidationError.get(0)));
    }

    @Test
    void shallReturnMultipleErrorMessageIfDatesAreBeforeMin() {
      validation = ElementValidationMedicalInvestigationList.builder()
          .min(Period.ofDays(10))
          .build();
      final var expectedValidationError = Stream.concat(getExpectedValidationError(
              "Ange ett datum som är tidigast " + LocalDate.now().plusDays(10) + ".",
              COMPLETE_MEDICAL_INV.date().dateId()
          ).stream(), getExpectedValidationError(
              "Ange ett datum som är tidigast " + LocalDate.now().plusDays(10) + ".",
              COMPLETE_MEDICAL_INV_2.date().dateId()
          ).stream()
      ).toList();

      final var categoryId = Optional.of(CATEGORY_ID);
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueMedicalInvestigationList.builder()
                  .id(FIELD_ID)
                  .list(
                      List.of(
                          COMPLETE_MEDICAL_INV, COMPLETE_MEDICAL_INV_2
                      )
                  )
                  .build()
          )
          .build();

      final var validationErrors = validation.validate(elementData, categoryId);
      assertTrue(validationErrors.contains(expectedValidationError.get(0)));
      assertTrue(validationErrors.contains(expectedValidationError.get(1)));
    }
  }

  @Nested
  class MandatoryErros {
    
  }

  private static List<ValidationError> getExpectedValidationError(String message, FieldId fieldId) {
    return List.of(
        ValidationError.builder()
            .elementId(ELEMENT_ID)
            .fieldId(fieldId)
            .categoryId(CATEGORY_ID)
            .message(new ErrorMessage(message))
            .build()
    );
  }
}
