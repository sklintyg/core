package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementDataConstants.CATEGORY_ELEMENT_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementDataConstants.DATE_ELEMENT_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementSpecification.DATE_ELEMENT_SPECIFICATION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementSpecification.categoryElementSpecificationBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementSpecification.dateElementSpecificationBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.service.PdfGeneratorOptions;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidation;
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
    void shallReturnEmptyIfShouldValidateReturnsFalse() {
      final var expectedResult = Collections.emptyList();
      final var validation = mock(ElementValidation.class);

      final var elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(mock(ElementValue.class))
          .build();

      final var element = dateElementSpecificationBuilder()
          .shouldValidate(elementDataList -> elementDataList.stream()
              .noneMatch(data -> data.id().equals(ELEMENT_ID))
          ).build();

      final var actualResult = element.validate(List.of(elementData), Optional.empty());
      verifyNoInteractions(validation);
      assertEquals(expectedResult, actualResult);
    }

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

      doReturn(expectedResult).when(validation).validate(elementData, Optional.empty(),
          Collections.emptyList());

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

      doReturn(expectedResult).when(validation).validate(elementData, Optional.empty(),
          List.of(elementData));

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

      doReturn(validationErrorsOne).when(validationOne).validate(elementData, Optional.empty(),
          List.of(elementData));
      doReturn(validationErrorsTwo).when(validationTwo).validate(elementData, Optional.empty(),
          List.of(elementData));

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

      final var elementData = List.of(elementDataOne, elementDataTwo);

      final var validationErrorsOne = List.of(
          ValidationError.builder().fieldId(FIELD_ID_ONE).build());
      final var validationErrorsTwo = List.of(
          ValidationError.builder().fieldId(FIELD_ID_TWO).build());

      doReturn(validationErrorsOne).when(validationOne).validate(elementDataOne, Optional.empty(),
          elementData);
      doReturn(validationErrorsTwo).when(validationTwo).validate(elementDataTwo, Optional.empty(),
          elementData);

      final var actualResult = element.validate(
          elementData,
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
          .validate(any(ElementData.class), eq(Optional.empty()), eq(List.of(elementData)));

      element.validate(List.of(elementData), Optional.empty());

      final var elementDataArgumentCaptor = ArgumentCaptor.forClass(ElementData.class);

      verify(validation).validate(elementDataArgumentCaptor.capture(), eq(Optional.empty()),
          eq(List.of(elementData)));

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
          .validate(any(ElementData.class), eq(Optional.empty()), eq(Collections.emptyList()));

      element.validate(Collections.emptyList(), Optional.empty());

      final var elementDataArgumentCaptor = ArgumentCaptor.forClass(ElementData.class);

      verify(validation).validate(elementDataArgumentCaptor.capture(), eq(Optional.empty()),
          eq(Collections.emptyList()));

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
          .validate(eq(elementData), any(Optional.class), eq(List.of(elementData)));

      element.validate(List.of(elementData), Optional.empty());

      final ArgumentCaptor<Optional<ElementId>> elementDataArgumentCaptor = ArgumentCaptor.forClass(
          Optional.class);
      verify(validation).validate(eq(elementData), elementDataArgumentCaptor.capture(),
          eq(List.of(elementData)));

      assertEquals(expectedCategory, elementDataArgumentCaptor.getValue().orElseThrow());
    }
  }

  @Test
  void shallReturnStreamOfElementSpecificationIncludingChildrenIfFlatten() {
    final var element = dateElementSpecificationBuilder()
        .id(new ElementId("ANOTHER_ELEMENT_ID"))
        .children(List.of(DATE_ELEMENT_SPECIFICATION))
        .build();

    final var response = element.flatten().toList();
    assertAll(
        () -> assertEquals(2, response.size()),
        () -> assertEquals(element, response.getFirst()),
        () -> assertEquals(DATE_ELEMENT_SPECIFICATION, response.getLast())
    );
  }

  @Nested
  class SimplifiedValue {

    private static final ElementId ELEMENT_ID = new ElementId("elementId");
    private static final ElementId HIDDEN_BY_ELEMENT_ID = new ElementId("hiddenByElementId");
    private static final List<ElementId> HIDDEN_ELEMENTS = List.of(HIDDEN_BY_ELEMENT_ID);
    private static final List<ElementData> ALL_ELEMENT_DATA = Collections.emptyList();

    private CitizenPdfConfiguration citizenPdfConfiguration;
    private ElementValue elementValue;
    private ElementSimplifiedValue expectedSimplifiedValue;
    private ElementSimplifiedValue replacementValue;
    private ElementConfiguration configuration;
    private ElementData elementData;

    @BeforeEach
    void setUp() {
      elementValue = mock(ElementValue.class);
      expectedSimplifiedValue = mock(ElementSimplifiedValue.class);
      replacementValue = mock(ElementSimplifiedValue.class);
      configuration = mock(ElementConfiguration.class);

      elementData = ElementData.builder()
          .id(ELEMENT_ID)
          .value(elementValue)
          .build();

      citizenPdfConfiguration = CitizenPdfConfiguration.builder()
          .hiddenBy(HIDDEN_BY_ELEMENT_ID)
          .replacementValue(replacementValue)
          .build();
    }

    @Test
    void shouldReturnEmptyValueWhenElementDataIsEmpty() {
      final var expected = Optional.of(ElementSimplifiedValueText.builder()
          .text("Ej angivet")
          .build());

      final var elementSpecification = dateElementSpecificationBuilder()
          .id(ELEMENT_ID)
          .build();

      final var options = PdfGeneratorOptions.builder()
          .citizenFormat(false)
          .hiddenElements(HIDDEN_ELEMENTS)
          .build();

      final var result = elementSpecification.simplifiedValue(
          Optional.empty(),
          ALL_ELEMENT_DATA,
          options
      );
      assertEquals(expected, result);
    }

    @Test
    void shouldReturnSimplifiedValueFromConfigurationIfNotCitizenFormat() {
      doReturn(Optional.of(expectedSimplifiedValue))
          .when(configuration).simplified(elementValue);

      final var elementSpecification = ElementSpecification.builder()
          .id(ELEMENT_ID)
          .configuration(configuration)
          .pdfConfiguration(citizenPdfConfiguration)
          .build();

      final var options = PdfGeneratorOptions.builder()
          .citizenFormat(false)
          .hiddenElements(HIDDEN_ELEMENTS)
          .build();

      final var result = elementSpecification.simplifiedValue(
          Optional.of(elementData),
          ALL_ELEMENT_DATA,
          options
      );

      assertEquals(Optional.of(expectedSimplifiedValue), result);
    }

    @Test
    void shouldReturnSimplifiedValueFromConfigurationIfNoCitizenPdfConfig() {
      doReturn(Optional.of(expectedSimplifiedValue))
          .when(configuration).simplified(elementValue);

      final var elementSpecification = ElementSpecification.builder()
          .id(ELEMENT_ID)
          .configuration(configuration)
          .pdfConfiguration(null)
          .build();

      final var options = PdfGeneratorOptions.builder()
          .citizenFormat(true)
          .hiddenElements(HIDDEN_ELEMENTS)
          .build();

      final var result = elementSpecification.simplifiedValue(
          Optional.of(elementData),
          ALL_ELEMENT_DATA,
          options
      );

      assertEquals(Optional.of(expectedSimplifiedValue), result);
    }

    @Test
    void shouldReturnReplacementValueWhenHiddenByCitizenChoice() {
      final var citizenPdfConfiguration = CitizenPdfConfiguration.builder()
          .hiddenBy(HIDDEN_BY_ELEMENT_ID)
          .replacementValue(replacementValue)
          .build();

      final var elementSpecification = ElementSpecification.builder()
          .id(ELEMENT_ID)
          .configuration(configuration)
          .pdfConfiguration(citizenPdfConfiguration)
          .build();

      final var options = PdfGeneratorOptions.builder()
          .citizenFormat(true)
          .hiddenElements(HIDDEN_ELEMENTS)
          .build();

      final var result = elementSpecification.simplifiedValue(
          Optional.of(elementData),
          ALL_ELEMENT_DATA,
          options
      );

      assertEquals(Optional.of(replacementValue), result);
    }

    @Test
    void shouldReturnReplacementValueWhenHiddenByValuePredicate() {
      final var shouldHidePredicate = mock(Predicate.class);
      doReturn(true).when(shouldHidePredicate).test(ALL_ELEMENT_DATA);

      final var citizenPdfConfiguration = CitizenPdfConfiguration.builder()
          .shouldHide(shouldHidePredicate)
          .replacementValue(replacementValue)
          .build();

      final var elementSpecification = ElementSpecification.builder()
          .id(ELEMENT_ID)
          .configuration(configuration)
          .pdfConfiguration(citizenPdfConfiguration)
          .build();

      final var options = PdfGeneratorOptions.builder()
          .citizenFormat(true)
          .hiddenElements(Collections.emptyList())
          .build();

      final var result = elementSpecification.simplifiedValue(
          Optional.of(elementData),
          ALL_ELEMENT_DATA,
          options
      );

      assertEquals(Optional.of(replacementValue), result);
      verify(shouldHidePredicate).test(ALL_ELEMENT_DATA);
    }

    @Test
    void shouldNotHideWhenHiddenByIsNullAndShouldHideIsFalse() {
      final var shouldHidePredicate = mock(Predicate.class);
      doReturn(false).when(shouldHidePredicate).test(ALL_ELEMENT_DATA);
      doReturn(Optional.of(expectedSimplifiedValue))
          .when(configuration).simplified(elementValue);

      final var citizenPdfConfiguration = CitizenPdfConfiguration.builder()
          .hiddenBy(null)
          .shouldHide(shouldHidePredicate)
          .replacementValue(replacementValue)
          .build();

      final var elementSpecification = ElementSpecification.builder()
          .id(ELEMENT_ID)
          .configuration(configuration)
          .pdfConfiguration(citizenPdfConfiguration)
          .build();

      final var options = PdfGeneratorOptions.builder()
          .citizenFormat(true)
          .hiddenElements(HIDDEN_ELEMENTS)
          .build();

      final var result = elementSpecification.simplifiedValue(
          Optional.of(elementData),
          ALL_ELEMENT_DATA,
          options
      );

      assertEquals(Optional.of(expectedSimplifiedValue), result);
      verify(shouldHidePredicate).test(ALL_ELEMENT_DATA);
      verify(configuration).simplified(elementValue);
    }

    @Test
    void shouldNotHideWhenElementNotInHiddenElementsList() {
      final var otherElementId = new ElementId("otherElementId");
      doReturn(Optional.of(expectedSimplifiedValue))
          .when(configuration).simplified(elementValue);

      final var citizenPdfConfiguration = CitizenPdfConfiguration.builder()
          .hiddenBy(otherElementId)
          .replacementValue(replacementValue)
          .build();

      final var elementSpecification = ElementSpecification.builder()
          .id(ELEMENT_ID)
          .configuration(configuration)
          .pdfConfiguration(citizenPdfConfiguration)
          .build();

      final var options = PdfGeneratorOptions.builder()
          .citizenFormat(true)
          .hiddenElements(HIDDEN_ELEMENTS)
          .build();

      final var result = elementSpecification.simplifiedValue(
          Optional.of(elementData),
          ALL_ELEMENT_DATA,
          options
      );

      assertEquals(Optional.of(expectedSimplifiedValue), result);
      verify(configuration).simplified(elementValue);
    }

    @Test
    void shouldIgnoreHiddenLogicWhenPdfConfigurationIsNotCitizenPdfConfiguration() {
      final var nonCitizenPdfConfiguration = mock(PdfConfiguration.class);
      doReturn(Optional.of(expectedSimplifiedValue))
          .when(configuration).simplified(elementValue);

      final var elementSpecification = ElementSpecification.builder()
          .id(ELEMENT_ID)
          .configuration(configuration)
          .pdfConfiguration(nonCitizenPdfConfiguration)
          .build();

      final var options = PdfGeneratorOptions.builder()
          .citizenFormat(true)
          .hiddenElements(HIDDEN_ELEMENTS)
          .build();

      final var result = elementSpecification.simplifiedValue(
          Optional.of(elementData),
          ALL_ELEMENT_DATA,
          options
      );

      assertEquals(Optional.of(expectedSimplifiedValue), result);
      verify(configuration).simplified(elementValue);
    }
  }
}

