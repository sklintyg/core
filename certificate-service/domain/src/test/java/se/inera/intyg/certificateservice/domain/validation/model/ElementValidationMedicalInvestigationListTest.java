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

  private static final ElementId ELEMENT_ID = new ElementId("elementId");
  private static final FieldId FIELD_ID = new FieldId("MedicalInvestigation");
  private static final ElementId CATEGORY_ID = new ElementId("categoryId");

  private static final MedicalInvestigation EMPTY_MEDICAL_INV = MedicalInvestigation.builder()
      .id(new FieldId("EMPTY_MI_1"))
      .date(ElementValueDate.builder().dateId(new FieldId("EMPTY_DATE_ID")).build())
      .investigationType(ElementValueCode.builder().codeId(new FieldId("EMPTY_CODE_ID")).build())
      .informationSource(ElementValueText.builder().textId(new FieldId("EMPTY_TEXT_ID")).build())
      .build();

  private static final MedicalInvestigation EMPTY_MEDICAL_INV_2 = MedicalInvestigation.builder()
      .id(new FieldId("EMPTY_MI_2"))
      .date(ElementValueDate.builder().dateId(new FieldId("EMPTY_DATE_ID_2")).build())
      .investigationType(ElementValueCode.builder().codeId(new FieldId("EMPTY_CODE_ID_2")).build())
      .informationSource(ElementValueText.builder().textId(new FieldId("EMPTY_TEXT_ID_2")).build())
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
  class EmptyStates {

    @Test
    void shallReturnEmptyListIfListIsEmpty() {
      validation = ElementValidationMedicalInvestigationList.builder().build();
      final Optional<ElementId> categoryId = Optional.empty();
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueMedicalInvestigationList.builder()
                  .list(Collections.emptyList())
                  .build()
          )
          .build();
      assertEquals(Collections.emptyList(), validation.validate(elementData, categoryId,
          Collections.emptyList()));
    }

    @Test
    void shallReturnEmptyListIfListIsNotInitialized() {
      validation = ElementValidationMedicalInvestigationList.builder().build();
      final Optional<ElementId> categoryId = Optional.empty();
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueMedicalInvestigationList.builder()
                  .list(List.of(MedicalInvestigation.builder().build()))
                  .build()
          )
          .build();
      assertEquals(Collections.emptyList(), validation.validate(elementData, categoryId,
          Collections.emptyList()));
    }
  }

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
          () -> validation.validate(null, categoryId, Collections.emptyList())
      );
    }

    @Test
    void shallThrowIllegalArgumentExceptionIfValueIsNull() {
      final Optional<ElementId> categoryId = Optional.empty();
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .build();

      assertThrows(IllegalArgumentException.class,
          () -> validation.validate(elementData, categoryId, Collections.emptyList()));
    }

    @Test
    void shallThrowIllegalArgumentExceptionIfValueIsWrongType() {
      final Optional<ElementId> categoryId = Optional.empty();
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(ElementValueUnitContactInformation.builder().build())
          .build();

      assertThrows(IllegalArgumentException.class,
          () -> validation.validate(elementData, categoryId, Collections.emptyList()));
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

      final var validationErrors = validation.validate(elementData, categoryId,
          Collections.emptyList());
      assertEquals(Collections.emptyList(), validationErrors);
    }

    @Test
    void shallReturnErrorMessageIfDateIsAfterMax() {
      validation = ElementValidationMedicalInvestigationList.builder()
          .max(Period.ofDays(0))
          .build();
      final var expectedValidationError = getExpectedValidationErrorAsList(
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

      final var validationErrors = validation.validate(elementData, categoryId,
          Collections.emptyList());
      assertTrue(validationErrors.contains(expectedValidationError.get(0)));
    }

    @Test
    void shallReturnMultipleErrorMessageIfDatesAreAfterMaxAndMaxIsSet() {
      validation = ElementValidationMedicalInvestigationList.builder()
          .max(Period.ofDays(0))
          .build();
      final var expectedValidationError = Stream.concat(getExpectedValidationErrorAsList(
              "Ange ett datum som är senast " + MAX_DATE + ".",
              COMPLETE_MEDICAL_INV.date().dateId()
          ).stream(), getExpectedValidationErrorAsList(
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

      final var validationErrors = validation.validate(elementData, categoryId,
          Collections.emptyList());
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

      final var validationErrors = validation.validate(elementData, categoryId,
          Collections.emptyList());
      assertEquals(Collections.emptyList(), validationErrors);
    }

    @Test
    void shallReturnErrorMessageIfDateIsBeforeMin() {
      validation = ElementValidationMedicalInvestigationList.builder()
          .min(Period.ofDays(10))
          .build();
      final var expectedValidationError = getExpectedValidationErrorAsList(
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

      final var validationErrors = validation.validate(elementData, categoryId,
          Collections.emptyList());
      assertTrue(validationErrors.contains(expectedValidationError.get(0)));
    }

    @Test
    void shallReturnMultipleErrorMessageIfDatesAreBeforeMin() {
      validation = ElementValidationMedicalInvestigationList.builder()
          .min(Period.ofDays(10))
          .build();
      final var expectedValidationError = Stream.concat(getExpectedValidationErrorAsList(
              "Ange ett datum som är tidigast " + LocalDate.now().plusDays(10) + ".",
              COMPLETE_MEDICAL_INV.date().dateId()
          ).stream(), getExpectedValidationErrorAsList(
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

      final var validationErrors = validation.validate(elementData, categoryId,
          Collections.emptyList());
      assertTrue(validationErrors.contains(expectedValidationError.get(0)));
      assertTrue(validationErrors.contains(expectedValidationError.get(1)));
    }
  }

  @Nested
  class MandatoryErrors {

    @Test
    void shouldReturnNoErrorsIfNotMandatory() {
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

      final var validationErrors = validation.validate(elementData, categoryId,
          Collections.emptyList());
      assertEquals(Collections.emptyList(), validationErrors);
    }

    @Test
    void shouldReturnMissingFieldsAndMandatoryValidationIfMandatoryAndFirstRowIncomplete() {
      validation = ElementValidationMedicalInvestigationList.builder()
          .mandatory(true)
          .build();
      final var categoryId = Optional.of(CATEGORY_ID);

      final var expectedValidationErrors = List.of(
          getExpectedValidationError("Ange ett svar.", INCOMPLETE_MEDICAL_INV_TEXT.id()),
          getExpectedValidationError("Ange ett svar.",
              INCOMPLETE_MEDICAL_INV_TEXT.informationSource()
                  .textId())
      );

      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueMedicalInvestigationList.builder()
                  .id(FIELD_ID)
                  .list(
                      List.of(
                          INCOMPLETE_MEDICAL_INV_TEXT
                      )
                  )
                  .build()
          )
          .build();

      final var validationErrors = validation.validate(elementData, categoryId,
          Collections.emptyList());
      assertEquals(expectedValidationErrors, validationErrors);
    }

    @Test
    void shouldReturnMissingFieldsAndMandatoryValidationIfMandatoryAndFirstRowEmpty() {
      validation = ElementValidationMedicalInvestigationList.builder()
          .mandatory(true)
          .build();
      final var categoryId = Optional.of(CATEGORY_ID);
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueMedicalInvestigationList.builder()
                  .id(FIELD_ID)
                  .list(
                      List.of(
                          EMPTY_MEDICAL_INV
                      )
                  )
                  .build()
          )
          .build();

      final var expectedValidationErrors = List.of(
          getExpectedValidationError("Ange ett svar.", EMPTY_MEDICAL_INV.id()),
          getExpectedValidationError("Ange ett datum.", EMPTY_MEDICAL_INV.date().dateId()),
          getExpectedValidationError("Välj ett alternativ.", EMPTY_MEDICAL_INV.investigationType()
              .codeId()),
          getExpectedValidationError("Ange ett svar.", EMPTY_MEDICAL_INV.informationSource()
              .textId())
      );
      final var validationErrors = validation.validate(elementData, categoryId,
          Collections.emptyList());
      assertEquals(expectedValidationErrors.size(), validationErrors.size());
      assertEquals(expectedValidationErrors, validationErrors);
    }

    @Test
    void shouldReturnWrongOrderAndMissingFieldsIfSecondRowIsIncompleteAndFirstIsEmpty() {
      validation = ElementValidationMedicalInvestigationList.builder()
          .mandatory(true)
          .build();
      final var categoryId = Optional.of(CATEGORY_ID);
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueMedicalInvestigationList.builder()
                  .id(FIELD_ID)
                  .list(
                      List.of(
                          EMPTY_MEDICAL_INV,
                          INCOMPLETE_MEDICAL_INV_DATE
                      )
                  )
                  .build()
          )
          .build();

      final var expectedValidationErrors = List.of(
          getExpectedValidationError("Fyll i fälten uppifrån och ned.",
              FIELD_ID),
          getExpectedValidationError("Ange ett datum.", EMPTY_MEDICAL_INV.date().dateId()),
          getExpectedValidationError("Välj ett alternativ.",
              EMPTY_MEDICAL_INV.investigationType().codeId()),
          getExpectedValidationError("Ange ett svar.",
              EMPTY_MEDICAL_INV.informationSource().textId()),
          getExpectedValidationError("Ange ett datum.",
              INCOMPLETE_MEDICAL_INV_DATE.date().dateId())
      );

      final var validationErrors = validation.validate(elementData, categoryId,
          Collections.emptyList());
      assertEquals(expectedValidationErrors.size(), validationErrors.size());
      assertEquals(expectedValidationErrors, validationErrors);
    }

    @Test
    void shouldReturnWrongOrderAndMissingFieldsIfSecondRowIsEmptyAndThirdAndFirstIsFilled() {
      validation = ElementValidationMedicalInvestigationList.builder()
          .mandatory(true)
          .build();
      final var categoryId = Optional.of(CATEGORY_ID);
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueMedicalInvestigationList.builder()
                  .id(FIELD_ID)
                  .list(
                      List.of(
                          COMPLETE_MEDICAL_INV,
                          EMPTY_MEDICAL_INV,
                          COMPLETE_MEDICAL_INV_2
                      )
                  )
                  .build()
          )
          .build();

      final var expectedValidationErrors = List.of(
          getExpectedValidationError("Fyll i fälten uppifrån och ned.",
              FIELD_ID),
          getExpectedValidationError("Ange ett datum.", EMPTY_MEDICAL_INV.date().dateId()),
          getExpectedValidationError("Välj ett alternativ.",
              EMPTY_MEDICAL_INV.investigationType().codeId()),
          getExpectedValidationError("Ange ett svar.",
              EMPTY_MEDICAL_INV.informationSource().textId())
      );

      final var validationErrors = validation.validate(elementData, categoryId,
          Collections.emptyList());
      assertEquals(expectedValidationErrors.size(), validationErrors.size());
      assertEquals(expectedValidationErrors, validationErrors);
    }

    @Test
    void shouldReturnWrongOrderAndMissingFieldsIfThirdIsIncomplete() {
      validation = ElementValidationMedicalInvestigationList.builder()
          .mandatory(true)
          .build();
      final var categoryId = Optional.of(CATEGORY_ID);
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueMedicalInvestigationList.builder()
                  .id(FIELD_ID)
                  .list(
                      List.of(
                          EMPTY_MEDICAL_INV,
                          EMPTY_MEDICAL_INV_2,
                          INCOMPLETE_MEDICAL_INV_CODE
                      )
                  )
                  .build()
          )
          .build();

      final var expectedValidationErrors = List.of(
          getExpectedValidationError("Fyll i fälten uppifrån och ned.",
              FIELD_ID),
          getExpectedValidationError("Ange ett datum.", EMPTY_MEDICAL_INV.date().dateId()),
          getExpectedValidationError("Välj ett alternativ.",
              EMPTY_MEDICAL_INV.investigationType().codeId()),
          getExpectedValidationError("Ange ett svar.",
              EMPTY_MEDICAL_INV.informationSource().textId()),
          getExpectedValidationError("Ange ett datum.", EMPTY_MEDICAL_INV_2.date().dateId()),
          getExpectedValidationError("Välj ett alternativ.",
              EMPTY_MEDICAL_INV_2.investigationType().codeId()),
          getExpectedValidationError("Ange ett svar.",
              EMPTY_MEDICAL_INV_2.informationSource().textId()),
          getExpectedValidationError("Välj ett alternativ.",
              INCOMPLETE_MEDICAL_INV_CODE.investigationType()
                  .codeId())
      );

      final var validationErrors = validation.validate(elementData, categoryId,
          Collections.emptyList());
      assertEquals(expectedValidationErrors.size(), validationErrors.size());
      assertEquals(expectedValidationErrors, validationErrors);
    }

    @Test
    void shouldReturnWrongOrderAndMissingFieldsIfFirstRowIsIncomplete() {
      validation = ElementValidationMedicalInvestigationList.builder()
          .mandatory(true)
          .build();
      final var categoryId = Optional.of(CATEGORY_ID);
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueMedicalInvestigationList.builder()
                  .id(FIELD_ID)
                  .list(
                      List.of(
                          INCOMPLETE_MEDICAL_INV_TEXT,
                          COMPLETE_MEDICAL_INV_2,
                          COMPLETE_MEDICAL_INV
                      )
                  )
                  .build()
          )
          .build();

      final var expectedValidationErrors = List.of(
          getExpectedValidationError("Ange ett svar.",
              INCOMPLETE_MEDICAL_INV_TEXT.informationSource().textId()),
          getExpectedValidationError("Fyll i fälten uppifrån och ned.",
              FIELD_ID)
      );

      final var validationErrors = validation.validate(elementData, categoryId,
          Collections.emptyList());
      assertEquals(expectedValidationErrors.size(), validationErrors.size());
      assertEquals(expectedValidationErrors, validationErrors);
    }
  }

  @Nested
  class TextLimitError {

    @Test
    void shouldReturnNoErrorsIfNoTextLimitIsSet() {
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

      final var validationErrors = validation.validate(elementData, categoryId,
          Collections.emptyList());
      assertEquals(Collections.emptyList(), validationErrors);
    }

    @Test
    void shouldReturnErrorIfTextIsOverLimit() {
      validation = ElementValidationMedicalInvestigationList.builder().limit(1).build();
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

      final var validationErrors = validation.validate(elementData, categoryId,
          Collections.emptyList());
      assertEquals(getExpectedValidationErrorAsList("Ange en text som inte är längre än 1.",
          COMPLETE_MEDICAL_INV.informationSource().textId()), validationErrors);
    }

    @Test
    void shouldReturnNoValidationErrorsIfFilledInCorecly() {
      validation = ElementValidationMedicalInvestigationList.builder()
          .limit(100)
          .mandatory(true)
          .max(Period.ofDays(10))
          .min(Period.ofDays(-10))
          .build();

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

      final var validationErrors = validation.validate(elementData, categoryId,
          Collections.emptyList());
      assertEquals(Collections.emptyList(), validationErrors);
    }
  }

  private static ValidationError getExpectedValidationError(String message, FieldId fieldId) {
    return ValidationError.builder()
        .elementId(ELEMENT_ID)
        .fieldId(fieldId)
        .categoryId(CATEGORY_ID)
        .message(new ErrorMessage(message))
        .build();
  }

  private static List<ValidationError> getExpectedValidationErrorAsList(String message,
      FieldId fieldId) {
    return List.of(
        getExpectedValidationError(message, fieldId)
    );
  }
}