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
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueUnitContactInformation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class ElementValidationCodeTest {

  private static final ElementId ELEMENT_ID = new ElementId("elementId");
  private static final FieldId FIELD_ID = new FieldId("fieldId");
  private static final ElementId CATEGORY_ID = new ElementId("categoryId");
  private ElementValidationCode elementValidationDate;

  @Nested
  class IllegalStates {

    @BeforeEach
    void setUp() {
      elementValidationDate = ElementValidationCode.builder().build();
    }

    @Test
    void shallThrowIllegalArgumentExceptionIfDataIsNull() {
      final Optional<ElementId> categoryId = Optional.empty();
      assertThrows(IllegalArgumentException.class,
          () -> elementValidationDate.validate(null, categoryId, Collections.emptyList())
      );
    }

    @Test
    void shallThrowIllegalArgumentExceptionIfValueIsNull() {
      final Optional<ElementId> categoryId = Optional.empty();
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .build();

      assertThrows(IllegalArgumentException.class,
          () -> elementValidationDate.validate(elementData, categoryId, Collections.emptyList()));
    }

    @Test
    void shallThrowIllegalArgumentExceptionIfValueIsWrongType() {
      final Optional<ElementId> categoryId = Optional.empty();
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(ElementValueUnitContactInformation.builder().build())
          .build();

      assertThrows(IllegalArgumentException.class,
          () -> elementValidationDate.validate(elementData, categoryId, Collections.emptyList()));
    }
  }

  @Nested
  class Mandatory {

    @Test
    void shallReturnValidationErrorIfMandatoryTrueAndCodeIsNull() {
      final var expectedValidationErrors = List.of(
          ValidationError.builder()
              .elementId(ELEMENT_ID)
              .fieldId(FIELD_ID)
              .categoryId(CATEGORY_ID)
              .message(new ErrorMessage("Välj ett alternativ."))
              .build()
      );

      elementValidationDate = ElementValidationCode.builder()
          .mandatory(true)
          .build();

      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(ElementValueCode.builder()
              .codeId(FIELD_ID)
              .build())
          .build();

      final var actualValidationErrors = elementValidationDate.validate(elementData,
          Optional.of(CATEGORY_ID), Collections.emptyList());
      assertEquals(expectedValidationErrors, actualValidationErrors);
    }

    @Test
    void shallNotReturnValidationErrorIfMandatoryTrueAndCodeHasValue() {
      elementValidationDate = ElementValidationCode.builder()
          .mandatory(false)
          .build();

      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(ElementValueCode.builder()
              .codeId(FIELD_ID)
              .code("code")
              .build())
          .build();

      final var actualValidationErrors = elementValidationDate.validate(elementData,
          Optional.of(CATEGORY_ID), Collections.emptyList());
      assertEquals(Collections.emptyList(), actualValidationErrors);
    }

    @Test
    void shallReturnValidationErrorIfMandatoryTrueAndCodeIsEmpty() {
      final var expectedValidationErrors = List.of(
          ValidationError.builder()
              .elementId(ELEMENT_ID)
              .fieldId(FIELD_ID)
              .categoryId(CATEGORY_ID)
              .message(new ErrorMessage("Välj ett alternativ."))
              .build()
      );

      elementValidationDate = ElementValidationCode.builder()
          .mandatory(true)
          .build();

      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(ElementValueCode.builder()
              .codeId(FIELD_ID)
              .code("")
              .build())
          .build();

      final var actualValidationErrors = elementValidationDate.validate(elementData,
          Optional.of(CATEGORY_ID), Collections.emptyList());
      assertEquals(expectedValidationErrors, actualValidationErrors);
    }

    @Test
    void shallNotReturnValidationErrorIfMandatoryFalseAndCodeIsNull() {
      elementValidationDate = ElementValidationCode.builder()
          .mandatory(false)
          .build();

      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(ElementValueCode.builder()
              .codeId(FIELD_ID)
              .build())
          .build();

      final var actualValidationErrors = elementValidationDate.validate(elementData,
          Optional.of(CATEGORY_ID), Collections.emptyList());
      assertEquals(Collections.emptyList(), actualValidationErrors);
    }

    @Test
    void shallNotReturnValidationErrorIfMandatoryFalseAndCodeIsEmpty() {
      elementValidationDate = ElementValidationCode.builder()
          .mandatory(false)
          .build();

      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(ElementValueCode.builder()
              .codeId(FIELD_ID)
              .code("")
              .build())
          .build();

      final var actualValidationErrors = elementValidationDate.validate(elementData,
          Optional.of(CATEGORY_ID), Collections.emptyList());
      assertEquals(Collections.emptyList(), actualValidationErrors);
    }
  }
}