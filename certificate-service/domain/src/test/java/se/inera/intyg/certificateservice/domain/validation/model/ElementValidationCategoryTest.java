package se.inera.intyg.certificateservice.domain.validation.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementData.textElementDataBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementDataConstants.TEXT_ELEMENT_FIELD_ID;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class ElementValidationCategoryTest {

  private static final ElementId ELEMENT_ID = new ElementId("elementId");
  private ElementValidationCategory elementValidationCategory;

  @Test
  void shallThrowIfDataIsNull() {
    elementValidationCategory = new ElementValidationCategory(true, List.of(ELEMENT_ID));
    assertThrows(IllegalArgumentException.class,
        () -> elementValidationCategory.validate(null, Optional.empty(), Collections.emptyList()));
  }

  @Test
  void shallReturnEmptyListIfMandatoryIsFalse() {
    elementValidationCategory = new ElementValidationCategory(false, List.of(ELEMENT_ID));
    final var validationErrors = elementValidationCategory.validate(ElementData.builder().build(),
        Optional.empty(),
        Collections.emptyList());
    assertTrue(validationErrors.isEmpty());
  }

  @Test
  void shallReturnValidationErrorsIfMandatoryIsTrueAndElementIsEmpty() {
    final var elementData = textElementDataBuilder()
        .value(
            ElementValueText.builder()
                .textId(new FieldId(TEXT_ELEMENT_FIELD_ID))
                .build()
        )
        .build();

    final var expectedValidationError = ValidationError.builder()
        .elementId(new ElementId(elementData.id().id()))
        .categoryId(elementData.id())
        .fieldId(new FieldId(elementData.id().id()))
        .message(new ErrorMessage("Besvara minst en av frågorna."))
        .build();

    elementValidationCategory = new ElementValidationCategory(true, List.of(elementData.id()));

    final var validationErrors = elementValidationCategory.validate(elementData,
        Optional.empty(),
        List.of(elementData));

    assertEquals(List.of(expectedValidationError), validationErrors);
  }

  @Test
  void shallNotReturnValidationErrorsIfMandatoryIsTrueAndElementHasValue() {
    final var elementData = textElementDataBuilder().build();

    elementValidationCategory = new ElementValidationCategory(true, List.of(elementData.id()));

    final var validationErrors = elementValidationCategory.validate(elementData,
        Optional.empty(),
        List.of(elementData));

    assertTrue(validationErrors.isEmpty());
  }
}