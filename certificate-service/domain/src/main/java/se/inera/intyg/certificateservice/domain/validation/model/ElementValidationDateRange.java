package se.inera.intyg.certificateservice.domain.validation.model;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRange;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@Value
@Getter(AccessLevel.NONE)
@Builder
public class ElementValidationDateRange implements ElementValidation {

  private static final String TO_SUFFIX = ".to";
  private static final String FROM_SUFFIX = ".from";
  private static final String RANGE_SUFFIX = ".range";
  boolean mandatory;

  @Override
  public List<ValidationError> validate(ElementData data,
      Optional<ElementId> categoryId, List<ElementData> dataList) {
    if (data == null) {
      throw new IllegalArgumentException("Element data is null");
    }

    final var dateRange = getValue(data.value());

    final var rangeValidationErrors = getDateRangeValidationErrors(data, categoryId, dateRange);
    if (rangeValidationErrors != null && !rangeValidationErrors.isEmpty()) {
      return rangeValidationErrors;
    }

    if (mandatory && isDateRangeEmpty(dateRange)) {
      return List.of(
          ValidationError.builder()
              .elementId(data.id())
              .fieldId(dateRange.id())
              .categoryId(categoryId.orElse(null))
              .message(ErrorMessageFactory.missingDateRange())
              .build()
      );
    }

    return Collections.emptyList();
  }

  private boolean isDateRangeEmpty(ElementValueDateRange dateRange) {
    return dateRange == null
        || (dateRange.toDate() == null && dateRange.fromDate() == null);
  }

  private List<ValidationError> getDateRangeValidationErrors(ElementData data,
      Optional<ElementId> categoryId, ElementValueDateRange dateRange) {
    if (dateRange == null) {
      return Collections.emptyList();
    }

    final var incompleteErrors = getIncompleteDateRangeErrors(data, categoryId, dateRange);
    final var toBeforeFromErrors = getToBeforeFromDateRangeErrors(data, categoryId, dateRange);

    return incompleteErrors.isEmpty() ? toBeforeFromErrors : incompleteErrors;
  }

  private List<ValidationError> getToBeforeFromDateRangeErrors(ElementData data,
      Optional<ElementId> categoryId,
      ElementValueDateRange dateRange) {
    return isToBeforeFrom(dateRange) ?
        List.of(errorMessage(
                data, getFieldId(dateRange.id(), RANGE_SUFFIX), categoryId,
                ErrorMessageFactory.endDateAfterStartDate()
            )
        ) : Collections.emptyList();
  }

  private List<ValidationError> getIncompleteDateRangeErrors(ElementData data,
      Optional<ElementId> categoryId, ElementValueDateRange dateRange) {
    return !isDateRangeEmpty(dateRange) && !isDateRangeComplete(dateRange) ?
        List.of(
            errorMessage(
                data, getFieldIdOfIncompleteDateRange(dateRange), categoryId,
                ErrorMessageFactory.missingDate()
            )
        ) : Collections.emptyList();
  }

  private ElementValueDateRange getValue(ElementValue value) {
    if (value == null) {
      throw new IllegalArgumentException("Element data value is null");
    }

    if (value instanceof ElementValueDateRange dateRangeValue) {
      return dateRangeValue;
    }

    throw new IllegalArgumentException(
        "Element data value %s is of wrong type".formatted(value.getClass())
    );
  }

  private boolean isDateRangeComplete(ElementValueDateRange value) {
    return value.fromDate() != null
        && value.toDate() != null;
  }

  private boolean isToBeforeFrom(ElementValueDateRange value) {
    if (value.fromDate() == null || value.toDate() == null) {
      return false;
    }
    return value.toDate().isBefore(value.fromDate());
  }

  private FieldId getFieldIdOfIncompleteDateRange(ElementValueDateRange dateRange) {
    return dateRange.toDate() == null
        ? getFieldId(dateRange.id(), TO_SUFFIX)
        : getFieldId(dateRange.id(), FROM_SUFFIX);
  }

  private FieldId getFieldId(FieldId id, String suffix) {
    return new FieldId(id.value() + suffix);
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
}