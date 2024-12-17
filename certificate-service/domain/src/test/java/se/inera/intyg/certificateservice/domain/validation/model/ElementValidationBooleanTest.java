package se.inera.intyg.certificateservice.domain.validation.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueUnitContactInformation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class ElementValidationBooleanTest {

  private static final ElementId ELEMENT_ID = new ElementId("elementId");
  private static final FieldId FIELD_ID = new FieldId("fieldId");
  private static final ElementId CATEGORY_ID = new ElementId("categoryId");
  private ElementValidationBoolean elementValidationBoolean;

  @Nested
  class IllegalStates {

    @BeforeEach
    void setUp() {
      elementValidationBoolean = ElementValidationBoolean.builder().build();
    }

    @Test
    void shallThrowIllegalArgumentExceptionIfDataIsNull() {
      final Optional<ElementId> categoryId = Optional.empty();
      assertThrows(IllegalArgumentException.class,
          () -> elementValidationBoolean.validate(null, categoryId, Collections.emptyList())
      );
    }

    @Test
    void shallThrowIllegalArgumentExceptionIfValueIsNull() {
      final Optional<ElementId> categoryId = Optional.empty();
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .build();

      assertThrows(IllegalArgumentException.class,
          () -> elementValidationBoolean.validate(elementData, categoryId,
              Collections.emptyList()));
    }

    @Test
    void shallThrowIllegalArgumentExceptionIfValueIsWrongType() {
      final Optional<ElementId> categoryId = Optional.empty();
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(ElementValueUnitContactInformation.builder().build())
          .build();

      assertThrows(IllegalArgumentException.class,
          () -> elementValidationBoolean.validate(elementData, categoryId,
              Collections.emptyList()));
    }
  }

  @Nested
  class Mandatory {

    @Test
    void shallReturnValidationErrorIfMandatoryTrueAndBooleanValueIsNull() {
      final var expectedValidationErrors = List.of(
          ValidationError.builder()
              .elementId(ELEMENT_ID)
              .fieldId(FIELD_ID)
              .categoryId(CATEGORY_ID)
              .message(new ErrorMessage("Välj ett alternativ."))
              .build()
      );

      elementValidationBoolean = ElementValidationBoolean.builder()
          .mandatory(true)
          .build();

      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(ElementValueBoolean.builder()
              .booleanId(FIELD_ID)
              .build())
          .build();

      final var actualValidationErrors = elementValidationBoolean.validate(elementData,
          Optional.of(CATEGORY_ID), Collections.emptyList());
      assertEquals(expectedValidationErrors, actualValidationErrors);
    }

    @Test
    void shallNotReturnValidationErrorIfMandatoryTrueAndBooleanValueHasValue() {
      elementValidationBoolean = ElementValidationBoolean.builder()
          .mandatory(true)
          .build();

      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(ElementValueBoolean.builder()
              .booleanId(FIELD_ID)
              .value(true)
              .build())
          .build();

      final var actualValidationErrors = elementValidationBoolean.validate(elementData,
          Optional.of(CATEGORY_ID), Collections.emptyList());

      assertEquals(Collections.emptyList(), actualValidationErrors);
    }

    @Test
    void shallNotReturnValidationErrorIfMandatoryFalseAndBooleanValueIsNull() {
      elementValidationBoolean = ElementValidationBoolean.builder()
          .mandatory(false)
          .build();

      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(ElementValueBoolean.builder()
              .booleanId(FIELD_ID)
              .build())
          .build();

      final var actualValidationErrors = elementValidationBoolean.validate(elementData,
          Optional.of(CATEGORY_ID), Collections.emptyList());

      assertEquals(Collections.emptyList(), actualValidationErrors);
    }
  }
}