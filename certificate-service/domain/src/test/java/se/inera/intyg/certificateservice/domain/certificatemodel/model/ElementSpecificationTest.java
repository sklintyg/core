package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementDataConstants.CATEGORY_ELEMENT_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementDataConstants.DATE_ELEMENT_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementSpecification.DATE_ELEMENT_SPECIFICATION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementSpecification.categoryElementSpecificationBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementSpecification.dateElementSpecificationBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.validation.model.ValidationError;

class ElementSpecificationTest {

  private static final ElementId ANOTHER_ELEMENT_ID = new ElementId("ANOTHER_ELEMENT_ID");

  @Nested
  class TestElementSpecificationExists {

    @Test
    void shallReturnTrueIfElementExitsOnRootLevel() {
      assertTrue(
          DATE_ELEMENT_SPECIFICATION.exists(new ElementId(DATE_ELEMENT_ID)),
          "Expected to find '%s' among specifications '%s'".formatted(DATE_ELEMENT_ID,
              DATE_ELEMENT_SPECIFICATION)
      );
    }

    @Test
    void shallReturnTrueIfElementExitsOnNextLevel() {
      final var element = dateElementSpecificationBuilder()
          .id(new ElementId("ANOTHER_ELEMENT_ID"))
          .children(List.of(DATE_ELEMENT_SPECIFICATION))
          .build();

      assertTrue(element.exists(new ElementId(DATE_ELEMENT_ID)),
          "Expected to find '%s' among specifications '%s'".formatted(DATE_ELEMENT_ID, element)
      );
    }
  }

  @Nested
  class TestElementSpecificationWithId {

    @Test
    void shallReturnElementIfElementExitsOnRootLevel() {
      assertEquals(DATE_ELEMENT_SPECIFICATION,
          DATE_ELEMENT_SPECIFICATION.elementSpecification(DATE_ELEMENT_SPECIFICATION.id())
      );
    }

    @Test
    void shallReturnElementIfElementExitsOnNextLevel() {
      final var element = dateElementSpecificationBuilder()
          .id(new ElementId("ANOTHER_ELEMENT_ID"))
          .children(List.of(DATE_ELEMENT_SPECIFICATION))
          .build();

      assertEquals(DATE_ELEMENT_SPECIFICATION,
          element.elementSpecification(DATE_ELEMENT_SPECIFICATION.id())
      );
    }

    @Test
    void shallThrowExceptionIfElementDoesntExists() {
      final var element = dateElementSpecificationBuilder()
          .id(new ElementId("ANOTHER_ELEMENT_ID"))
          .build();

      final var elementId = DATE_ELEMENT_SPECIFICATION.id();
      assertThrows(IllegalArgumentException.class,
          () -> element.elementSpecification(elementId)
      );
    }

    @Test
    void shallThrowExceptionIfElementDoesntExistsOnNextLevel() {
      final var element = dateElementSpecificationBuilder()
          .id(ANOTHER_ELEMENT_ID)
          .children(
              List.of(
                  dateElementSpecificationBuilder()
                      .id(ANOTHER_ELEMENT_ID)
                      .build()
              )
          )
          .build();

      final var elementId = DATE_ELEMENT_SPECIFICATION.id();
      assertThrows(IllegalArgumentException.class,
          () -> element.elementSpecification(elementId)
      );
    }
  }

  @Nested
  class TestValidate {

    private static final ElementId ELEMENT_ID = new ElementId("elementId");
    private static final FieldId FIELD_ID_ONE = new FieldId("dateElementValueId");
    private static final FieldId FIELD_ID_TWO = new FieldId("fieldIdTwo");
    private static final ElementId CATEGORY_ID = new ElementId("categoryId");

    @Test
    void shallReturnEmptyIfValid() {
      final var expectedResult = Collections.emptyList();
      final var validation = mock(ElementValidation.class);
      final var element = dateElementSpecificationBuilder()
          .id(ELEMENT_ID)
          .validations(List.of(validation))
          .build();

      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(mock(ElementValue.class))
          .build();

      doReturn(expectedResult).when(validation).validate(elementData, Optional.empty());

      final var actualResult = element.validate(List.of(elementData), Optional.empty());
      assertEquals(expectedResult, actualResult);
    }

    @Test
    void shallReturnValidationErrorIfInvalid() {
      final var expectedResult = List.of(ValidationError.builder().build());
      final var validation = mock(ElementValidation.class);
      final var element = dateElementSpecificationBuilder()
          .id(ELEMENT_ID)
          .validations(List.of(validation))
          .build();

      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(mock(ElementValue.class))
          .build();

      doReturn(expectedResult).when(validation).validate(elementData, Optional.empty());

      final var actualResult = element.validate(List.of(elementData), Optional.empty());
      assertEquals(expectedResult, actualResult);
    }

    @Test
    void shallReturnMergedValidationErrorIfMultipleValidationIsInvalid() {
      final var expectedResult = List.of(
          ValidationError.builder().fieldId(FIELD_ID_ONE).build(),
          ValidationError.builder().fieldId(FIELD_ID_TWO).build()
      );
      final var validationOne = mock(ElementValidation.class);
      final var validationTwo = mock(ElementValidation.class);
      final var element = dateElementSpecificationBuilder()
          .id(ELEMENT_ID)
          .validations(
              List.of(validationOne, validationTwo)
          )
          .build();

      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(mock(ElementValue.class))
          .build();

      final var validationErrorsOne = List.of(
          ValidationError.builder().fieldId(FIELD_ID_ONE).build());
      final var validationErrorsTwo = List.of(
          ValidationError.builder().fieldId(FIELD_ID_TWO).build());

      doReturn(validationErrorsOne).when(validationOne).validate(elementData, Optional.empty());
      doReturn(validationErrorsTwo).when(validationTwo).validate(elementData, Optional.empty());

      final var actualResult = element.validate(List.of(elementData), Optional.empty());
      assertEquals(expectedResult, actualResult);
    }

    @Test
    void shallReturnMergedValidationErrorIfChildIsAlsoInvalid() {
      final var expectedResult = List.of(
          ValidationError.builder().fieldId(FIELD_ID_ONE).build(),
          ValidationError.builder().fieldId(FIELD_ID_TWO).build()
      );
      final var validationOne = mock(ElementValidation.class);
      final var validationTwo = mock(ElementValidation.class);

      final var childElement = dateElementSpecificationBuilder()
          .id(ANOTHER_ELEMENT_ID)
          .validations(
              List.of(validationTwo)
          )
          .build();

      final var elementDataTwo = ElementData.builder()
          .id(ANOTHER_ELEMENT_ID)
          .value(mock(ElementValue.class))
          .build();

      final var element = dateElementSpecificationBuilder()
          .id(ELEMENT_ID)
          .validations(
              List.of(validationOne)
          )
          .children(List.of(childElement))
          .build();

      final var elementDataOne = ElementData.builder()
          .id(ELEMENT_ID)
          .value(mock(ElementValue.class))
          .build();

      final var validationErrorsOne = List.of(
          ValidationError.builder().fieldId(FIELD_ID_ONE).build());
      final var validationErrorsTwo = List.of(
          ValidationError.builder().fieldId(FIELD_ID_TWO).build());

      doReturn(validationErrorsOne).when(validationOne).validate(elementDataOne, Optional.empty());
      doReturn(validationErrorsTwo).when(validationTwo).validate(elementDataTwo, Optional.empty());

      final var actualResult = element.validate(
          List.of(elementDataOne, elementDataTwo),
          Optional.empty()
      );
      assertEquals(expectedResult, actualResult);
    }

    @Test
    void shallCreateEmptyValueIfMissingInData() {
      final var expectedValue = ElementValueDate.builder()
          .dateId(FIELD_ID_ONE)
          .build();

      final var validation = mock(ElementValidation.class);
      final var element = dateElementSpecificationBuilder()
          .id(ELEMENT_ID)
          .validations(List.of(validation))
          .build();

      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .build();

      doReturn(Collections.emptyList()).when(validation)
          .validate(any(ElementData.class), eq(Optional.empty()));

      element.validate(List.of(elementData), Optional.empty());

      final var elementDataArgumentCaptor = ArgumentCaptor.forClass(ElementData.class);

      verify(validation).validate(elementDataArgumentCaptor.capture(), eq(Optional.empty()));

      assertEquals(expectedValue, elementDataArgumentCaptor.getValue().value());
    }

    @Test
    void shallCreateEmptyDataIfMissingInData() {
      final var expectedValue = ElementData.builder()
          .id(ELEMENT_ID)
          .value(
              ElementValueDate.builder()
                  .dateId(FIELD_ID_ONE)
                  .build()
          )
          .build();

      final var validation = mock(ElementValidation.class);
      final var element = dateElementSpecificationBuilder()
          .id(ELEMENT_ID)
          .validations(List.of(validation))
          .build();

      doReturn(Collections.emptyList()).when(validation)
          .validate(any(ElementData.class), eq(Optional.empty()));

      element.validate(Collections.emptyList(), Optional.empty());

      final var elementDataArgumentCaptor = ArgumentCaptor.forClass(ElementData.class);

      verify(validation).validate(elementDataArgumentCaptor.capture(), eq(Optional.empty()));

      assertEquals(expectedValue, elementDataArgumentCaptor.getValue());
    }

    @Test
    void shallUseCategoryIdForChildren() {
      final var expectedCategory = new ElementId(CATEGORY_ELEMENT_ID);
      final var validation = mock(ElementValidation.class);
      final var element = categoryElementSpecificationBuilder()
          .children(
              List.of(
                  dateElementSpecificationBuilder()
                      .id(ELEMENT_ID)
                      .validations(List.of(validation))
                      .build()
              )
          )
          .build();

      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(mock(ElementValue.class))
          .build();

      doReturn(Collections.emptyList()).when(validation)
          .validate(eq(elementData), any(Optional.class));

      element.validate(List.of(elementData), Optional.empty());

      final ArgumentCaptor<Optional<ElementId>> elementDataArgumentCaptor = ArgumentCaptor.forClass(
          Optional.class);
      verify(validation).validate(eq(elementData), elementDataArgumentCaptor.capture());

      assertEquals(expectedCategory, elementDataArgumentCaptor.getValue().orElseThrow());
    }
  }
}