/*
 * Copyright (C) 2024 Inera AB (http://www.inera.se)
 *
 * This file is part of sklintyg (https://github.com/sklintyg).
 *
 * sklintyg is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * sklintyg is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package se.inera.intyg.certificateservice.domain.validation.model;

import static se.inera.intyg.certificateservice.domain.certificatemodel.model.CheckboxDateRange.RANGE_SUFFIX;

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

    final var incompleteErrors = getIncompleteDateRangeErrors(data, categoryId, dateRangeList);
    final var incorrectErrors = getIncorrectDateRangeErrors(data, categoryId, dateRangeList);

    return Stream
        .concat(incompleteErrors.stream(), incorrectErrors.stream())
        .toList();
  }

  private List<ValidationError> getOverlappingErrors(ElementData data,
      Optional<ElementId> categoryId, ElementValueDateRangeList dateRangeList) {
    if (dateRangeList.dateRangeList() == null) {
      return Collections.emptyList();
    }

    final var hasOverlap = dateRangeList.dateRangeList().stream()
        .anyMatch(dateRange -> doesDateRangeHaveOverlap(dateRange, dateRangeList.dateRangeList()));

    return !hasOverlap ? Collections.emptyList()
        : List.of(
            errorMessage(data, dateRangeList.dateRangeListId(), categoryId,
                "Ange sjukskrivningsperioder som inte överlappar varandra.")
        );
  }

  private boolean doesDateRangeHaveOverlap(DateRange dateRange, List<DateRange> list) {
    return list.stream()
        .filter(comparedDateRange -> comparedDateRange.dateRangeId() != dateRange.dateRangeId())
        .anyMatch(comparedDateRange -> doesDateRangesOverlap(comparedDateRange, dateRange));
  }

  private boolean doesDateRangesOverlap(DateRange dateRange1, DateRange dateRange2) {
    final var overlap = Math.min(dateRange1.to().toEpochDay(), dateRange2.to().toEpochDay()) -
        Math.max(dateRange1.from().toEpochDay(), dateRange2.from().toEpochDay());

    return overlap >= 0;
  }

  private List<ValidationError> getIncorrectDateRangeErrors(ElementData data,
      Optional<ElementId> categoryId,
      ElementValueDateRangeList dateRangeList) {
    return dateRangeList.dateRangeList().stream()
        .filter(this::isDateRangeIncorrect)
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

  private boolean isDateRangeIncorrect(DateRange value) {
    if (value.from() == null || value.to() == null) {
      return false;
    }
    return value.from().isAfter(value.to());
  }

  private FieldId getFieldIdOfIncompleteDateRange(DateRange dateRange) {
    return dateRange.to() == null ?
        getFieldId(dateRange.dateRangeId(), CheckboxDateRange.TO_SUFFIX)
        : getFieldId(dateRange.dateRangeId(), CheckboxDateRange.FROM_SUFFIX);
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

}
