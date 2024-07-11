package se.inera.intyg.certificateservice.domain.validation.model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAmount;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@Value
@Getter(AccessLevel.NONE)
@Builder
public class ElementValidationDateList implements ElementValidation {

  boolean mandatory;
  TemporalAmount max;

  @Override
  public List<ValidationError> validate(ElementData data, Optional<ElementId> categoryId) {
    validateElementData(data);
    final var dateList = getValue(data.value());
    final var dateAfterMaxErrors = getDateAfterMaxErrors(data, categoryId, dateList);
    if (!dateAfterMaxErrors.isEmpty()) {
      return dateAfterMaxErrors;
    }

    if (mandatory && !doesDateListHaveValue(dateList)) {
      return List.of(
          errorMessage(
              data,
              dateList.dateListId(),
              categoryId,
              ErrorMessageFactory.missingMultipleOption()
          )
      );
    }

    return Collections.emptyList();
  }

  private static void validateElementData(ElementData data) {
    if (data == null) {
      throw new IllegalArgumentException("Element data is null");
    }
    if (data.value() == null) {
      throw new IllegalArgumentException("Element data value is null");
    }
  }


  private List<ValidationError> getDateAfterMaxErrors(ElementData data,
      Optional<ElementId> categoryId, ElementValueDateList dateList) {
    return dateList.dateList().stream()
        .filter(valueDate -> isAfterMax(valueDate.date()))
        .map(dateRange -> errorMessage(
                data,
                dateRange.dateId(),
                categoryId,
                ErrorMessageFactory.maxDate(maxDate())
            )
        )
        .toList();
  }

  private ElementValueDateList getValue(ElementValue value) {
    if (value instanceof ElementValueDateList dateList) {
      return dateList;
    }

    throw new IllegalArgumentException(
        "Element data value %s is of wrong type".formatted(value.getClass())
    );

  }

  private boolean isAfterMax(LocalDate value) {
    return value != null && max != null && value.isAfter(maxDate());
  }

  private boolean doesDateListHaveValue(ElementValueDateList value) {
    if (value.dateList() == null || value.dateList().isEmpty()) {
      return false;
    }

    return value.dateList().stream()
        .anyMatch(valueDate -> valueDate.date() != null);
  }
  
  private static ValidationError errorMessage(ElementData data,
      FieldId fieldId,
      Optional<ElementId> categoryId, ErrorMessage message) {
    return
        ValidationError.builder()
            .elementId(data.id())
            .fieldId(fieldId)
            .categoryId(categoryId.orElse(null))
            .message(message)
            .build();
  }

  private LocalDate maxDate() {
    return LocalDate.now(ZoneId.systemDefault()).plus(max);
  }
}
