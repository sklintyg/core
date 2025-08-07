package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.time.Period;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.DateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRangeList;
import se.inera.intyg.certificateservice.domain.common.model.Code;

@Value
@Builder
public class ElementConfigurationCheckboxDateRangeList implements ElementConfiguration {

  @Getter(onMethod = @__(@Override))
  String name;
  @Getter(onMethod = @__(@Override))
  String description;
  @Getter(onMethod = @__(@Override))
  String label;
  @Getter(onMethod = @__(@Override))
  ElementType type = ElementType.CHECKBOX_DATE_RANGE_LIST;
  @Getter(onMethod = @__(@Override))
  ElementMessage message;

  FieldId id;
  boolean hideWorkingHours;
  List<ElementConfigurationCode> dateRanges;
  Period min;
  Period max;

  @Override
  public ElementValue emptyValue() {
    return ElementValueDateRangeList.builder()
        .dateRangeListId(id)
        .dateRangeList(Collections.emptyList())
        .build();
  }

  public Optional<ElementConfigurationCode> checkboxDateRange(FieldId id) {
    if (dateRanges == null) {
      return Optional.empty();
    }

    return dateRanges.stream()
        .filter(dateRange -> dateRange.id().equals(id))
        .findFirst();
  }

  public Code code(DateRange dateRange) {
    return dateRanges.stream()
        .filter(configurationCode -> configurationCode.id().equals(dateRange.dateRangeId()))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException(
                "Cannot find matching code for dateId '%s'".formatted(dateRange.dateRangeId())
            )
        )
        .code();
  }
}
