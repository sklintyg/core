package se.inera.intyg.certificateservice.domain.validation.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class ElementValidationDiagnosisTest {

  private static final String MANDATORY_FIELD = "mandatoryField";
  private static final FieldId MANDATORY_FIELD_ID = new FieldId(MANDATORY_FIELD);
  private static final String CATEGORY_ID = "categoryId";
  private static final Optional<ElementId> CATEGORY_ELEMENT_ID = Optional.of(
      new ElementId(CATEGORY_ID));
  ElementValidationDiagnosis elementValidationDiagnosis;


  @Test
  void shallThrowIfElementDataIsNull() {
    elementValidationDiagnosis = ElementValidationDiagnosis.builder().build();
    assertThrows(IllegalArgumentException.class,
        () -> elementValidationDiagnosis.validate(null, CATEGORY_ELEMENT_ID));
  }

  @Nested
  class WithMandatoryField {

    @BeforeEach
    void setUp() {
      elementValidationDiagnosis = ElementValidationDiagnosis.builder()
          .mandatoryField(MANDATORY_FIELD_ID)
          .build();
    }


  }
}
