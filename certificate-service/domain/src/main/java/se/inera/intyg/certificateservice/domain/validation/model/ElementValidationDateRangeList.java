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
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRangeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

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

    if (mandatory && !isDateRangeListFilled(dateRangeList)) {
      return errorMessage(data, dateRangeList, categoryId, "Ange ett datum.");
    }

    return Collections.emptyList();
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
    return value.dateRangeList().stream()
        .noneMatch(this::isDateRangeComplete);
  }

  private boolean isDateRangeComplete(ElementValueDateRange value) {
    return value.from() != null
        && value.to() != null;
  }

  private static List<ValidationError> errorMessage(ElementData data,
      ElementValueDateRangeList dateValue,
      Optional<ElementId> categoryId, String message) {
    return List.of(
        ValidationError.builder()
            .elementId(data.id())
            .fieldId(dateValue.dateRangeListId())
            .categoryId(categoryId.orElse(null))
            .message(new ErrorMessage(message))
            .build()
    );
  }

}
