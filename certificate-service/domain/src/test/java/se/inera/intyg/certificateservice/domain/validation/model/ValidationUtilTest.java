package se.inera.intyg.certificateservice.domain.validation.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class ValidationUtilTest {

  @Test
  void shouldReturnValidationError() {
    final var expected = ValidationError.builder()
        .elementId(new ElementId("ELEMENT_ID"))
        .categoryId(new ElementId("CATEGORY_ID"))
        .fieldId(new FieldId("FIELD_ID"))
        .message(new ErrorMessage("MESSAGE"))
        .build();

    final var response = ValidationUtil.errorMessage(
        ElementData.builder().id(new ElementId("ELEMENT_ID")).build(),
        new FieldId("FIELD_ID"),
        Optional.of(new ElementId("CATEGORY_ID")),
        "MESSAGE"
    );

    assertEquals(expected, response);
  }

  @Test
  void shouldReturnValidationErrorWithoutCategoryId() {
    final var expected = ValidationError.builder()
        .elementId(new ElementId("ELEMENT_ID"))
        .fieldId(new FieldId("FIELD_ID"))
        .message(new ErrorMessage("MESSAGE"))
        .build();

    final var response = ValidationUtil.errorMessage(
        ElementData.builder().id(new ElementId("ELEMENT_ID")).build(),
        new FieldId("FIELD_ID"),
        Optional.empty(),
        "MESSAGE"
    );

    assertEquals(expected, response);
  }

}