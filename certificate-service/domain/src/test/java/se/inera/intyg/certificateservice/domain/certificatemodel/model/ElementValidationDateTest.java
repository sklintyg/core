package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.validation.model.ErrorMessage;
import se.inera.intyg.certificateservice.domain.validation.model.ValidationError;

class ElementValidationDateTest {

  private static final ElementId ELEMENT_ID = new ElementId("elementId");
  private static final FieldId FIELD_ID = new FieldId("fieldId");
  private static final ElementId CATEGORY_ID = new ElementId("categoryId");
  private ElementValidationDate elementValidationDate;

  @BeforeEach
  void setUp() {
    elementValidationDate = ElementValidationDate.builder()
        .mandatory(true)
        .min(Period.ofDays(0))
        .max(Period.ofDays(30))
        .build();
  }

  @Test
  void shallReturnValidationErrorIfDateIsNull() {
    final var expectedValidationError = List.of(
        ValidationError.builder()
            .elementId(ELEMENT_ID)
            .fieldId(FIELD_ID)
            .categoryId(CATEGORY_ID)
            .message(new ErrorMessage("Ange ett datum."))
            .build()
    );

    final var elementData = ElementData.builder()
        .id(ELEMENT_ID)
        .value(
            ElementValueDate.builder()
                .dateId(FIELD_ID)
                .build()
        )
        .build();

    final var actualResult = elementValidationDate.validate(elementData,
        Optional.of(CATEGORY_ID));
    assertEquals(expectedValidationError, actualResult);
  }

  @Test
  void shallReturnValidationErrorIfDateIsBeforeMin() {
    final var expectedValidationError = List.of(
        ValidationError.builder()
            .elementId(ELEMENT_ID)
            .fieldId(FIELD_ID)
            .categoryId(CATEGORY_ID)
            .message(
                new ErrorMessage("Ange ett datum som är tidigast %s.".formatted(LocalDate.now())))
            .build()
    );

    final var elementData = ElementData.builder()
        .id(ELEMENT_ID)
        .value(
            ElementValueDate.builder()
                .dateId(FIELD_ID)
                .date(LocalDate.now().minusDays(1))
                .build()
        )
        .build();

    final var actualResult = elementValidationDate.validate(elementData,
        Optional.of(CATEGORY_ID));
    assertEquals(expectedValidationError, actualResult);
  }

  @Test
  void shallReturnValidationErrorIfDateIsAfterMax() {
    final var expectedValidationError = List.of(
        ValidationError.builder()
            .elementId(ELEMENT_ID)
            .fieldId(FIELD_ID)
            .categoryId(CATEGORY_ID)
            .message(
                new ErrorMessage(
                    "Ange ett datum som är senast %s.".formatted(LocalDate.now().plusDays(30))))
            .build()
    );

    final var elementData = ElementData.builder()
        .id(ELEMENT_ID)
        .value(
            ElementValueDate.builder()
                .dateId(FIELD_ID)
                .date(LocalDate.now().plusDays(31))
                .build()
        )
        .build();

    final var actualResult = elementValidationDate.validate(elementData,
        Optional.of(CATEGORY_ID));
    assertEquals(expectedValidationError, actualResult);
  }
}