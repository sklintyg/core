package se.inera.intyg.certificateservice.domain.validation.model;

import java.time.temporal.TemporalAmount;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.DateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRangeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@Value
@Getter(AccessLevel.NONE)
@Builder
public class ElementValidationDateRangeList implements ElementValidation {

  private static final String TO_SUFFIX = ".to";
  private static final String FROM_SUFFIX = ".from";
  private static final String RANGE_SUFFIX = ".range";
  boolean mandatory;
  TemporalAmount min;
  TemporalAmount max;

  @Override
  public List<ValidationError> validate(ElementData data,
      Optional<ElementId> categoryId, List<ElementData> dataList) {
    if (data == null) {
      throw new IllegalArgumentException("Element data is null");
    }

    final var dateRangeList = getValue(data.value());

    final var childValidationErrors = getChildValidationErrors(data, categoryId, dateRangeList);
    if (childValidationErrors != null && !childValidationErrors.isEmpty()) {
      return childValidationErrors;
    }

    final var overlappingErrors = getOverlappingErrors(data, categoryId, dateRangeList);
    if (overlappingErrors != null && !overlappingErrors.isEmpty()) {
      return overlappingErrors;
    }

    if (mandatory && dateRangeList.isEmpty()) {
      return List.of(
          ValidationError.builder()
              .elementId(data.id())
              .fieldId(dateRangeList.dateRangeListId())
              .categoryId(categoryId.orElse(null))
              .message(ErrorMessageFactory.missingMultipleOption())
              .build()
      );
    }

    return Collections.emptyList();
  }

  private List<ValidationError> getChildValidationErrors(ElementData data,
      Optional<ElementId> categoryId, ElementValueDateRangeList dateRangeList) {
    if (dateRangeList.dateRangeList() == null) {
      return Collections.emptyList();
    }

    final var fromBeforeMinErrors = getFromBeforeMinErrors(data, categoryId, dateRangeList);
    final var incompleteErrors = getIncompleteDateRangeErrors(data, categoryId, dateRangeList);
    final var toBeforeMinErrors = getToBeforeMinErrors(data, categoryId, dateRangeList);
    final var fromAfterMaxErrors = getFromAfterMaxErrors(data, categoryId, dateRangeList);
    final var toAfterMaxErrors = getToAfterMaxErrors(data, categoryId, dateRangeList);
    final var toBeforeFromErrors = getToBeforeFromDateRangeErrors(data, categoryId, dateRangeList);

    return Stream.of(fromBeforeMinErrors, incompleteErrors, toBeforeMinErrors, toBeforeFromErrors,
            fromAfterMaxErrors, toAfterMaxErrors)
        .flatMap(List::stream)
        .toList();
  }

  private List<ValidationError> getOverlappingErrors(ElementData data,
      Optional<ElementId> categoryId, ElementValueDateRangeList dateRangeList) {
    if (dateRangeList.dateRangeList() == null) {
      return Collections.emptyList();
    }

    return dateRangeList.dateRangeList().stream()
        .filter(dateRange -> doesDateRangeHaveOverlap(dateRange, dateRangeList.dateRangeList()))
        .findFirst()
        .map(dateRange -> errorMessage(
                data,
                dateRangeList.dateRangeListId(),
                categoryId,
                ErrorMessageFactory.overlappingPeriods()
            )
        )
        .stream().toList();


  }

  private boolean doesDateRangeHaveOverlap(DateRange dateRange, List<DateRange> list) {
    return list.stream()
        .filter(comparedDateRange -> comparedDateRange.dateRangeId() != dateRange.dateRangeId())
        .anyMatch(comparedDateRange -> doesDateRangesOverlap(comparedDateRange, dateRange));
  }

  private boolean doesDateRangesOverlap(DateRange dateRange1, DateRange dateRange2) {
    final var overlap = Math.min(dateRange1.to().toEpochDay(), dateRange2.to().toEpochDay())
        - Math.max(dateRange1.from().toEpochDay(), dateRange2.from().toEpochDay());

    return overlap >= 0;
  }

  private List<ValidationError> getToBeforeFromDateRangeErrors(ElementData data,
      Optional<ElementId> categoryId,
      ElementValueDateRangeList dateRangeList) {
    return dateRangeList.dateRangeList().stream()
        .filter(this::isToBeforeFrom)
        .map(dateRange -> errorMessage(
                data, getFieldId(dateRange.dateRangeId(), RANGE_SUFFIX), categoryId,
                ErrorMessageFactory.endDateAfterStartDate()
            )
        )
        .toList();
  }

  private List<ValidationError> getIncompleteDateRangeErrors(ElementData data,
      Optional<ElementId> categoryId, ElementValueDateRangeList dateRangeList) {
    return dateRangeList.dateRangeList().stream()
        .filter(dateRange -> !isDateRangeComplete(dateRange))
        .map(dateRange -> errorMessage(
                data, getFieldIdOfIncompleteDateRange(dateRange), categoryId,
                ErrorMessageFactory.missingDate()
            )
        )
        .toList();
  }

  private List<ValidationError> getFromBeforeMinErrors(ElementData data,
      Optional<ElementId> categoryId, ElementValueDateRangeList dateRangeList) {
    return dateRangeList.dateRangeList().stream()
        .filter(dateRange -> ElementValidator.isDateBeforeMin(dateRange.from(), min))
        .map(dateRange -> errorMessage(
                data, getFieldId(dateRange.dateRangeId(), FROM_SUFFIX), categoryId,
                ErrorMessageFactory.minDate(min)
            )
        )
        .toList();
  }

  private List<ValidationError> getToBeforeMinErrors(ElementData data,
      Optional<ElementId> categoryId, ElementValueDateRangeList dateRangeList) {
    return dateRangeList.dateRangeList().stream()
        .filter(dateRange -> ElementValidator.isDateBeforeMin(dateRange.to(), min))
        .map(dateRange -> errorMessage(
                data, getFieldId(dateRange.dateRangeId(), TO_SUFFIX), categoryId,
                ErrorMessageFactory.minDate(min)
            )
        )
        .toList();
  }

  private List<ValidationError> getFromAfterMaxErrors(ElementData data,
      Optional<ElementId> categoryId, ElementValueDateRangeList dateRangeList) {
    return dateRangeList.dateRangeList().stream()
        .filter(dateRange -> ElementValidator.isDateAfterMax(dateRange.from(), max))
        .map(dateRange -> errorMessage(
                data, getFieldId(dateRange.dateRangeId(), FROM_SUFFIX), categoryId,
                ErrorMessageFactory.maxDate(max)
            )
        )
        .toList();
  }

  private List<ValidationError> getToAfterMaxErrors(ElementData data,
      Optional<ElementId> categoryId, ElementValueDateRangeList dateRangeList) {
    return dateRangeList.dateRangeList().stream()
        .filter(dateRange -> ElementValidator.isDateAfterMax(dateRange.to(), max))
        .map(dateRange -> errorMessage(
                data, getFieldId(dateRange.dateRangeId(), TO_SUFFIX), categoryId,
                ErrorMessageFactory.maxDate(max)
            )
        )
        .toList();
  }

  private ElementValueDateRangeList getValue(ElementValue value) {
    if (value == null) {
      throw new IllegalArgumentException("Element data value is null");
    }

    if (value instanceof ElementValueDateRangeList dateRangeListValue) {
      return dateRangeListValue;
    }

    throw new IllegalArgumentException(
        "Element data value %s is of wrong type".formatted(value.getClass())
    );

  }

  private boolean isDateRangeComplete(DateRange value) {
    return value.from() != null
        && value.to() != null;
  }

  private boolean isToBeforeFrom(DateRange value) {
    if (value.from() == null || value.to() == null) {
      return false;
    }
    return value.to().isBefore(value.from());
  }

  private FieldId getFieldIdOfIncompleteDateRange(DateRange dateRange) {
    return dateRange.to() == null
        ? getFieldId(dateRange.dateRangeId(), TO_SUFFIX)
        : getFieldId(dateRange.dateRangeId(), FROM_SUFFIX);
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