package se.inera.intyg.certificateservice.domain.validation.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueUnitContactInformation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@ExtendWith(MockitoExtension.class)
class ElementValidationTextTest {

  private static final ElementId ELEMENT_ID = new ElementId("elementId");
  private static final FieldId FIELD_ID = new FieldId("code");
  private static final ElementId CATEGORY_ID = new ElementId("categoryId");
  private ElementValidationText elementValidationText;

  @Nested
  class IllegalStates {

    @BeforeEach
    void setUp() {
      elementValidationText = ElementValidationText.builder().build();
    }

    @Test
    void shallThrowIllegalArgumentExceptionIfDataIsNull() {
      final Optional<ElementId> categoryId = Optional.empty();
      assertThrows(IllegalArgumentException.class,
          () -> elementValidationText.validate(null, categoryId, Collections.emptyList())
      );
    }

    @Test
    void shallThrowIllegalArgumentExceptionIfValueIsNull() {
      final Optional<ElementId> categoryId = Optional.empty();
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .build();

      assertThrows(IllegalArgumentException.class,
          () -> elementValidationText.validate(elementData, categoryId, Collections.emptyList())
      );
    }

    @Test
    void shallThrowIllegalArgumentExceptionIfValueIsWrongType() {
      final Optional<ElementId> categoryId = Optional.empty();
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(ElementValueUnitContactInformation.builder().build())
          .build();

      assertThrows(IllegalArgumentException.class,
          () -> elementValidationText.validate(elementData, categoryId, Collections.emptyList())
      );
    }
  }

  @Nested
  class Mandatory {

    @BeforeEach
    void setUp() {
      elementValidationText = ElementValidationText.builder()
          .mandatory(true)
          .limit(10)
          .build();
    }

    @Test
    void shallReturnValidationErrorIfTextIsNull() {
      final var expectedValidationError = List.of(
          ValidationError.builder()
              .elementId(ELEMENT_ID)
              .fieldId(FIELD_ID)
              .categoryId(CATEGORY_ID)
              .message(new ErrorMessage("Ange ett svar."))
              .build()
      );
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueText.builder()
                  .textId(FIELD_ID)
                  .build()
          )
          .build();

      final var actualResult = elementValidationText.validate(
          elementData,
          Optional.of(CATEGORY_ID),
          Collections.emptyList());

      assertEquals(expectedValidationError, actualResult);
    }

    @Test
    void shallReturnValidationErrorIfTextIsOverLimit() {
      final var expectedValidationError = List.of(
          ValidationError.builder()
              .elementId(ELEMENT_ID)
              .fieldId(FIELD_ID)
              .categoryId(CATEGORY_ID)
              .message(new ErrorMessage("Ange en text som inte är längre än 10."))
              .build()
      );
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueText.builder()
                  .textId(FIELD_ID)
                  .text("1234567891011213")
                  .build()
          )
          .build();

      final var actualResult = elementValidationText.validate(
          elementData,
          Optional.of(CATEGORY_ID),
          Collections.emptyList());

      assertEquals(expectedValidationError, actualResult);
    }

    @Test
    void shallNotReturnValidationErrorIfTextIsUnderLimit() {
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueText.builder()
                  .textId(FIELD_ID)
                  .text("123")
                  .build()
          )
          .build();

      final var actualResult = elementValidationText.validate(
          elementData,
          Optional.of(CATEGORY_ID),
          Collections.emptyList());

      assertEquals(Collections.emptyList(), actualResult);
    }
  }

  @Nested
  class NotMandatory {

    @BeforeEach
    void setUp() {
      elementValidationText = ElementValidationText.builder()
          .mandatory(false)
          .limit(10)
          .build();
    }

    @Test
    void shallNotReturnValidationErrorIfTextIsNull() {
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueText.builder()
                  .textId(FIELD_ID)
                  .build()
          )
          .build();

      final var actualResult = elementValidationText.validate(
          elementData,
          Optional.of(CATEGORY_ID),
          Collections.emptyList());

      assertEquals(Collections.emptyList(), actualResult);
    }

    @Test
    void shallNotReturnValidationErrorIfTextIsUnderLimit() {
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueText.builder()
                  .textId(FIELD_ID)
                  .text("123")
                  .build()
          )
          .build();

      final var actualResult = elementValidationText.validate(
          elementData,
          Optional.of(CATEGORY_ID),
          Collections.emptyList());

      assertEquals(Collections.emptyList(), actualResult);
    }

    @Test
    void shallReturnValidationErrorIfTextIsOverLimit() {
      final var expectedValidationError = List.of(
          ValidationError.builder()
              .elementId(ELEMENT_ID)
              .fieldId(FIELD_ID)
              .categoryId(CATEGORY_ID)
              .message(new ErrorMessage("Ange en text som inte är längre än 10."))
              .build()
      );
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueText.builder()
                  .textId(FIELD_ID)
                  .text("1234567891011213")
                  .build()
          )
          .build();

      final var actualResult = elementValidationText.validate(
          elementData,
          Optional.of(CATEGORY_ID),
          Collections.emptyList());

      assertEquals(expectedValidationError, actualResult);
    }
  }

  @Nested
  class NoLimits {

    @BeforeEach
    void setUp() {
      elementValidationText = ElementValidationText.builder()
          .build();
    }

    @Test
    void shallNotReturnValidationErrorIfTextExists() {
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueText.builder()
                  .textId(FIELD_ID)
                  .text("123")
                  .build()
          )
          .build();

      final var actualResult = elementValidationText.validate(
          elementData,
          Optional.of(CATEGORY_ID),
          Collections.emptyList());

      assertEquals(Collections.emptyList(), actualResult);
    }

    @Test
    void shallNotReturnValidationErrorIfTextIsEmpty() {
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueText.builder()
                  .textId(FIELD_ID)
                  .build()
          )
          .build();

      final var actualResult = elementValidationText.validate(
          elementData,
          Optional.of(CATEGORY_ID),
          Collections.emptyList());

      assertEquals(Collections.emptyList(), actualResult);
    }
  }
}