package se.inera.intyg.certificateservice.domain.validation.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosis;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosisList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class ElementValidationDiagnosisTest {

  private static final String MANDATORY_FIELD = "mandatoryField";
  private static final FieldId MANDATORY_FIELD_ID = new FieldId(MANDATORY_FIELD);
  private static final String CATEGORY_ID = "categoryId";
  private static final Optional<ElementId> CATEGORY_ELEMENT_ID = Optional.of(
      new ElementId(CATEGORY_ID));
  private static final ElementId ELEMENT_ID = new ElementId("elementId");
  private static final FieldId DIAGNOS_ONE = new FieldId("diagnos1");
  private static final String CODE = "code";
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

    @Test
    void shallGiveValidationErrorIfNoDiagnoses() {
      final var expectedValidationError = ValidationError.builder()
          .elementId(ELEMENT_ID)
          .fieldId(MANDATORY_FIELD_ID)
          .categoryId(CATEGORY_ELEMENT_ID.orElseThrow())
          .message(new ErrorMessage("Ange minst en diagnos."))
          .build();

      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueDiagnosisList.builder()
                  .build()
          )
          .build();

      final var validationErrors = elementValidationDiagnosis.validate(elementData,
          CATEGORY_ELEMENT_ID);

      assertEquals(expectedValidationError, validationErrors.get(0));
    }

    @Test
    void shallGiveValidationErrorIfDiagnosesAddedButMissingMandatoryFieldDiagnosis() {
      final var expectedValidationError = ValidationError.builder()
          .elementId(ELEMENT_ID)
          .fieldId(MANDATORY_FIELD_ID)
          .categoryId(CATEGORY_ELEMENT_ID.orElseThrow())
          .message(new ErrorMessage("Ange diagnos på översta raden först."))
          .build();

      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueDiagnosisList.builder()
                  .diagnoses(
                      List.of(
                          ElementValueDiagnosis.builder()
                              .id(DIAGNOS_ONE)
                              .code(CODE)
                              .build()
                      )
                  )
                  .build()
          )
          .build();

      final var validationErrors = elementValidationDiagnosis.validate(elementData,
          CATEGORY_ELEMENT_ID);

      assertEquals(expectedValidationError, validationErrors.get(0));
    }
  }
}