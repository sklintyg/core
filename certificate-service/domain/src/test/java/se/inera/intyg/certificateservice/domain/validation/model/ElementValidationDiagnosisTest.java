package se.inera.intyg.certificateservice.domain.validation.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosis;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosisList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.diagnosiscode.model.Diagnosis;
import se.inera.intyg.certificateservice.domain.diagnosiscode.model.DiagnosisCode;
import se.inera.intyg.certificateservice.domain.diagnosiscode.repository.DiagnosisCodeRepository;

@ExtendWith(MockitoExtension.class)
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
  private static final FieldId DIAGNOS_FIVE = new FieldId("diagnos5");
  private static final String DESCRIPTION = "description";
  @Mock
  DiagnosisCodeRepository diagnosisCodeRepository;
  ElementValidationDiagnosis elementValidationDiagnosis;


  @Test
  void shallThrowIfElementDataIsNull() {
    elementValidationDiagnosis = ElementValidationDiagnosis.builder().build();
    assertThrows(IllegalArgumentException.class,
        () -> elementValidationDiagnosis.validate(null, CATEGORY_ELEMENT_ID,
            Collections.emptyList()));
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
          CATEGORY_ELEMENT_ID, Collections.emptyList());

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
          CATEGORY_ELEMENT_ID, Collections.emptyList());

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
          CATEGORY_ELEMENT_ID, Collections.emptyList());
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
          CATEGORY_ELEMENT_ID, Collections.emptyList());
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
          CATEGORY_ELEMENT_ID, Collections.emptyList());
      assertEquals(Collections.emptyList(), validationErrors);
    }
  }

  @Nested
  class WithOrder {

    @BeforeEach
    void setUp() {
      elementValidationDiagnosis = ElementValidationDiagnosis.builder()
          .order(List.of(DIAGNOS_ONE, DIAGNOS_TWO, DIAGNOS_THREE, DIAGNOS_FOUR, DIAGNOS_FIVE))
          .build();
    }

    @Test
    void shallGiveMultipleValidationOrderIfOrderIsNotFollowed() {
      final var expectedValidationError = List.of(
          ValidationError.builder()
              .elementId(ELEMENT_ID)
              .fieldId(DIAGNOS_FOUR)
              .categoryId(CATEGORY_ELEMENT_ID.orElseThrow())
              .message(new ErrorMessage("Ange diagnoskod."))
              .build(),
          ValidationError.builder()
              .elementId(ELEMENT_ID)
              .fieldId(new FieldId(DIAGNOS_FOUR.value()))
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
              .fieldId(new FieldId(DIAGNOS_TWO.value()))
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
                              .description(DESCRIPTION)
                              .build(),
                          ElementValueDiagnosis.builder()
                              .id(DIAGNOS_THREE)
                              .code(CODE)
                              .description(DESCRIPTION)
                              .build(),
                          ElementValueDiagnosis.builder()
                              .id(DIAGNOS_FIVE)
                              .code(CODE)
                              .description(DESCRIPTION)
                              .build()
                      )
                  )
                  .build()
          )
          .build();

      final var validationErrors = elementValidationDiagnosis.validate(elementData,
          CATEGORY_ELEMENT_ID, Collections.emptyList());
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
              .fieldId(new FieldId(DIAGNOS_TWO.value()))
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
                              .description(DESCRIPTION)
                              .build(),
                          ElementValueDiagnosis.builder()
                              .id(DIAGNOS_THREE)
                              .code(CODE)
                              .description(DESCRIPTION)
                              .build(),
                          ElementValueDiagnosis.builder()
                              .id(DIAGNOS_FOUR)
                              .code(CODE)
                              .description(DESCRIPTION)
                              .build()
                      )
                  )
                  .build()
          )
          .build();

      final var validationErrors = elementValidationDiagnosis.validate(elementData,
          CATEGORY_ELEMENT_ID, Collections.emptyList());
      assertEquals(expectedValidationError, validationErrors);
    }

    @Test
    void shallGiveValidationOrderIfOrderIsNotFollowedAndDiagnosisCodeIsNull() {
      final var expectedValidationError = List.of(
          ValidationError.builder()
              .elementId(ELEMENT_ID)
              .fieldId(DIAGNOS_TWO)
              .categoryId(CATEGORY_ELEMENT_ID.orElseThrow())
              .message(new ErrorMessage("Ange diagnoskod."))
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
                              .description(DESCRIPTION)
                              .build(),
                          ElementValueDiagnosis.builder()
                              .id(DIAGNOS_TWO)
                              .description(DESCRIPTION)
                              .build(),
                          ElementValueDiagnosis.builder()
                              .id(DIAGNOS_THREE)
                              .code(CODE)
                              .description(DESCRIPTION)
                              .build(),
                          ElementValueDiagnosis.builder()
                              .id(DIAGNOS_FOUR)
                              .code(CODE)
                              .description(DESCRIPTION)
                              .build()
                      )
                  )
                  .build()
          )
          .build();

      final var validationErrors = elementValidationDiagnosis.validate(elementData,
          CATEGORY_ELEMENT_ID, Collections.emptyList());
      assertEquals(expectedValidationError, validationErrors);
    }

    @Test
    void shallGiveValidationOrderIfOrderIsNotFollowedAndDiagnosisDescriptionIsNull() {
      final var expectedValidationError = List.of(
          ValidationError.builder()
              .elementId(ELEMENT_ID)
              .fieldId(DIAGNOS_TWO)
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
                              .description(DESCRIPTION)
                              .build(),
                          ElementValueDiagnosis.builder()
                              .id(DIAGNOS_TWO)
                              .code(CODE)
                              .build(),
                          ElementValueDiagnosis.builder()
                              .id(DIAGNOS_THREE)
                              .code(CODE)
                              .description(DESCRIPTION)
                              .build(),
                          ElementValueDiagnosis.builder()
                              .id(DIAGNOS_FOUR)
                              .code(CODE)
                              .description(DESCRIPTION)
                              .build()
                      )
                  )
                  .build()
          )
          .build();

      final var validationErrors = elementValidationDiagnosis.validate(elementData,
          CATEGORY_ELEMENT_ID, Collections.emptyList());
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
          CATEGORY_ELEMENT_ID, Collections.emptyList());
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
          CATEGORY_ELEMENT_ID, Collections.emptyList());
      assertEquals(Collections.emptyList(), validationErrors);
    }
  }

  @Nested
  class ValidateCodeTests {

    @BeforeEach
    void setUp() {
      elementValidationDiagnosis = ElementValidationDiagnosis.builder()
          .diagnosisCodeRepository(diagnosisCodeRepository)
          .build();
    }

    @Test
    void shallGiveValidationErrorIfCodeNotValid() {
      final var expectedValidationError = List.of(
          ValidationError.builder()
              .elementId(ELEMENT_ID)
              .fieldId(DIAGNOS_ONE)
              .categoryId(CATEGORY_ELEMENT_ID.orElseThrow())
              .message(new ErrorMessage("Diagnoskod är ej giltig."))
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
                              .build()
                      )
                  )
                  .build()
          )
          .build();

      doReturn(Optional.empty()).when(diagnosisCodeRepository).findByCode(new DiagnosisCode(CODE));
      final var validationErrors = elementValidationDiagnosis.validate(elementData,
          CATEGORY_ELEMENT_ID, Collections.emptyList());
      assertEquals(expectedValidationError, validationErrors);
    }

    @Test
    void shallNotGiveValidationErrorIfCodeIsValid() {
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

      final var diagnosis = Diagnosis.builder().build();
      doReturn(Optional.of(diagnosis)).when(diagnosisCodeRepository)
          .findByCode(new DiagnosisCode(CODE));

      final var validationErrors = elementValidationDiagnosis.validate(elementData,
          CATEGORY_ELEMENT_ID, Collections.emptyList());
      assertTrue(validationErrors.isEmpty());
    }
  }
}