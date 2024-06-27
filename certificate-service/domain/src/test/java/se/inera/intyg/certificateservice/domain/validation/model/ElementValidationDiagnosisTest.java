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
  private static final FieldId DIAGNOS_TWO = new FieldId("diagnos2");
  private static final FieldId DIAGNOS_THREE = new FieldId("diagnos3");
  private static final FieldId DIAGNOS_FOUR = new FieldId("diagnos4");
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

    @Test
    void shallNotGiveValidationErrorIfMandatoryFieldHasValue() {
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueDiagnosisList.builder()
                  .diagnoses(
                      List.of(
                          ElementValueDiagnosis.builder()
                              .id(new FieldId(MANDATORY_FIELD))
                              .code(CODE)
                              .build()
                      )
                  )
                  .build()
          )
          .build();

      final var validationErrors = elementValidationDiagnosis.validate(elementData,
          CATEGORY_ELEMENT_ID);
      assertEquals(Collections.emptyList(), validationErrors);
    }
  }

  @Nested
  class WithoutMandatoryField {

    @BeforeEach
    void setUp() {
      elementValidationDiagnosis = ElementValidationDiagnosis.builder()
          .build();
    }

    @Test
    void shallNotGiveValidationErrorIfNoDiagnoses() {
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueDiagnosisList.builder()
                  .build()
          )
          .build();

      final var validationErrors = elementValidationDiagnosis.validate(elementData,
          CATEGORY_ELEMENT_ID);
      assertEquals(Collections.emptyList(), validationErrors);
    }

    @Test
    void shallNotGiveValidationErrorIfDiagnosesArePresent() {
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
      assertEquals(Collections.emptyList(), validationErrors);
    }
  }

  @Nested
  class WithOrder {

    @BeforeEach
    void setUp() {
      elementValidationDiagnosis = ElementValidationDiagnosis.builder()
          .order(List.of(DIAGNOS_ONE, DIAGNOS_TWO, DIAGNOS_THREE, DIAGNOS_FOUR))
          .build();
    }

    @Test
    void shallGiveMultipleValidationOrderIfOrderIsNotFollowed() {
      final var expectedValidationError = List.of(
          ValidationError.builder()
              .elementId(ELEMENT_ID)
              .fieldId(DIAGNOS_THREE)
              .categoryId(CATEGORY_ELEMENT_ID.orElseThrow())
              .message(new ErrorMessage("Ange diagnoskod."))
              .build(),
          ValidationError.builder()
              .elementId(ELEMENT_ID)
              .fieldId(new FieldId(DIAGNOS_THREE.value() + ".description"))
              .categoryId(CATEGORY_ELEMENT_ID.orElseThrow())
              .message(new ErrorMessage("Ange diagnosbeskrivning."))
              .build(),
          ValidationError.builder()
              .elementId(ELEMENT_ID)
              .fieldId(DIAGNOS_TWO)
              .categoryId(CATEGORY_ELEMENT_ID.orElseThrow())
              .message(new ErrorMessage("Ange diagnoskod."))
              .build(),
          ValidationError.builder()
              .elementId(ELEMENT_ID)
              .fieldId(new FieldId(DIAGNOS_TWO.value() + ".description"))
              .categoryId(CATEGORY_ELEMENT_ID.orElseThrow())
              .message(new ErrorMessage("Ange diagnosbeskrivning."))
              .build()
      );

      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueDiagnosisList.builder()
                  .diagnoses(
                      List.of(
                          ElementValueDiagnosis.builder()
                              .id(DIAGNOS_ONE)
                              .code(CODE)
                              .build(),
                          ElementValueDiagnosis.builder()
                              .id(DIAGNOS_FOUR)
                              .code(CODE)
                              .build()
                      )
                  )
                  .build()
          )
          .build();

      final var validationErrors = elementValidationDiagnosis.validate(elementData,
          CATEGORY_ELEMENT_ID);
      assertEquals(expectedValidationError, validationErrors);
    }

    @Test
    void shallGiveValidationOrderIfOrderIsNotFollowed() {
      final var expectedValidationError = List.of(
          ValidationError.builder()
              .elementId(ELEMENT_ID)
              .fieldId(DIAGNOS_TWO)
              .categoryId(CATEGORY_ELEMENT_ID.orElseThrow())
              .message(new ErrorMessage("Ange diagnoskod."))
              .build(),
          ValidationError.builder()
              .elementId(ELEMENT_ID)
              .fieldId(new FieldId(DIAGNOS_TWO.value() + ".description"))
              .categoryId(CATEGORY_ELEMENT_ID.orElseThrow())
              .message(new ErrorMessage("Ange diagnosbeskrivning."))
              .build()
      );

      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueDiagnosisList.builder()
                  .diagnoses(
                      List.of(
                          ElementValueDiagnosis.builder()
                              .id(DIAGNOS_ONE)
                              .code(CODE)
                              .build(),
                          ElementValueDiagnosis.builder()
                              .id(DIAGNOS_THREE)
                              .code(CODE)
                              .build(),
                          ElementValueDiagnosis.builder()
                              .id(DIAGNOS_FOUR)
                              .code(CODE)
                              .build()
                      )
                  )
                  .build()
          )
          .build();

      final var validationErrors = elementValidationDiagnosis.validate(elementData,
          CATEGORY_ELEMENT_ID);
      assertEquals(expectedValidationError, validationErrors);
    }
  }

  @Nested
  class WithoutOrder {

    @BeforeEach
    void setUp() {
      elementValidationDiagnosis = ElementValidationDiagnosis.builder()
          .build();
    }

    @Test
    void shallNotGiveMultipleValidationOrderIfOrderIsNotFollowed() {

      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueDiagnosisList.builder()
                  .diagnoses(
                      List.of(
                          ElementValueDiagnosis.builder()
                              .id(DIAGNOS_ONE)
                              .code(CODE)
                              .build(),
                          ElementValueDiagnosis.builder()
                              .id(DIAGNOS_FOUR)
                              .code(CODE)
                              .build()
                      )
                  )
                  .build()
          )
          .build();

      final var validationErrors = elementValidationDiagnosis.validate(elementData,
          CATEGORY_ELEMENT_ID);
      assertEquals(Collections.emptyList(), validationErrors);
    }

    @Test
    void shallNotGiveValidationOrderIfOrderIsNotFollowed() {
      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueDiagnosisList.builder()
                  .diagnoses(
                      List.of(
                          ElementValueDiagnosis.builder()
                              .id(DIAGNOS_ONE)
                              .code(CODE)
                              .build(),
                          ElementValueDiagnosis.builder()
                              .id(DIAGNOS_THREE)
                              .code(CODE)
                              .build(),
                          ElementValueDiagnosis.builder()
                              .id(DIAGNOS_FOUR)
                              .code(CODE)
                              .build()
                      )
                  )
                  .build()
          )
          .build();

      final var validationErrors = elementValidationDiagnosis.validate(elementData,
          CATEGORY_ELEMENT_ID);
      assertEquals(Collections.emptyList(), validationErrors);
    }
  }
}
