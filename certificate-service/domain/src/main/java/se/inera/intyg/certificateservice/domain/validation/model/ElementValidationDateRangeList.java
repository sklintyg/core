package se.inera.intyg.certificateservice.domain.validation.model;

import static se.inera.intyg.certificateservice.domain.certificatemodel.model.CheckboxDateRange.FROM_SUFFIX;
import static se.inera.intyg.certificateservice.domain.certificatemodel.model.CheckboxDateRange.RANGE_SUFFIX;
import static se.inera.intyg.certificateservice.domain.certificatemodel.model.CheckboxDateRange.TO_SUFFIX;

import java.time.LocalDate;
import java.time.ZoneId;
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
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CheckboxDateRange;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@Value
@Getter(AccessLevel.NONE)
@Builder
public class ElementValidationDateRangeList implements ElementValidation {

  boolean mandatory;
  TemporalAmount min;

  @Override
  public List<ValidationError> validate(ElementData data,
      Optional<ElementId> categoryId) {
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

    if (mandatory && !isDateRangeListFilled(dateRangeList)) {
      return List.of(
          errorMessage(
              data,
              dateRangeList.dateRangeListId(),
              categoryId,
              "Välj minst ett alternativ."
          )
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
    final var toBeforeMinErrors = getToBeforeMinErrors(data, categoryId, dateRangeList);
    final var incompleteErrors = getIncompleteDateRangeErrors(data, categoryId, dateRangeList);
    final var toBeforeFromErrors = getToBeforeFromDateRangeErrors(data, categoryId, dateRangeList);

    return Stream.of(fromBeforeMinErrors, toBeforeMinErrors, incompleteErrors, toBeforeFromErrors)
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
                "Ange perioder som inte överlappar varandra."
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
                "Ange ett slutdatum som infaller efter startdatumet."
            )
        )
        .toList();
  }

  private List<ValidationError> getIncompleteDateRangeErrors(ElementData data,
      Optional<ElementId> categoryId, ElementValueDateRangeList dateRangeList) {
    return dateRangeList.dateRangeList().stream()
        .filter(dateRange -> !isDateRangeComplete(dateRange))
        .map(dateRange -> errorMessage(
                data, getFieldIdOfIncompleteDateRange(dateRange), categoryId, "Ange ett datum."
            )
        )
        .toList();
  }

  private List<ValidationError> getFromBeforeMinErrors(ElementData data,
      Optional<ElementId> categoryId, ElementValueDateRangeList dateRangeList) {
    return dateRangeList.dateRangeList().stream()
        .filter(dateRange -> isBeforeMin(dateRange.from()))
        .map(dateRange -> errorMessage(
                data, getFieldId(dateRange.dateRangeId(), FROM_SUFFIX), categoryId,
                "Ange ett datum som är tidigast %s.".formatted(minDate())
            )
        )
        .toList();
  }

  private List<ValidationError> getToBeforeMinErrors(ElementData data,
      Optional<ElementId> categoryId, ElementValueDateRangeList dateRangeList) {
    return dateRangeList.dateRangeList().stream()
        .filter(dateRange -> isBeforeMin(dateRange.to()))
        .map(dateRange -> errorMessage(
                data, getFieldId(dateRange.dateRangeId(), TO_SUFFIX), categoryId,
                "Ange ett datum som är tidigast %s.".formatted(minDate())
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

  private boolean isBeforeMin(LocalDate value) {
    return value != null && min != null && value.isBefore(minDate());
  }

  private boolean isDateRangeListFilled(ElementValueDateRangeList value) {
    if (value.dateRangeList() == null) {
      return false;
    }

    return value.dateRangeList().stream()
        .anyMatch(this::isDateRangeComplete);
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
        ? getFieldId(dateRange.dateRangeId(), CheckboxDateRange.TO_SUFFIX)
        : getFieldId(dateRange.dateRangeId(), FROM_SUFFIX);
  }

  private FieldId getFieldId(FieldId id, String suffix) {
    return new FieldId(id.value() + suffix);
  }

  private static ValidationError errorMessage(ElementData data,
      FieldId fieldId,
      Optional<ElementId> categoryId, String message) {
    return
        ValidationError.builder()
            .elementId(data.id())
            .fieldId(fieldId)
            .categoryId(categoryId.orElse(null))
            .message(new ErrorMessage(message))
            .build();
  }

  private LocalDate minDate() {
    return LocalDate.now(ZoneId.systemDefault()).plus(min);
  }
}
