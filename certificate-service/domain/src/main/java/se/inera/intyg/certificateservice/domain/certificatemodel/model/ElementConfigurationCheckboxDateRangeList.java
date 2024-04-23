package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRangeList;

@Value
@Builder
public class ElementConfigurationCheckboxDateRangeList implements ElementConfiguration {

  @Getter(onMethod = @__(@Override))
  String name;
  @Getter(onMethod = @__(@Override))
  String label;
  @Getter(onMethod = @__(@Override))
  ElementType type = ElementType.CHECKBOX_DATE_RANGE_LIST;
  FieldId id;
  String previousDateRangeText;
  boolean hideWorkingHours;
  List<CheckboxDateRange> dateRanges;

  @Override
  public ElementValue emptyValue() {
    return ElementValueDateRangeList.builder()
        .dateRangeListId(id)
        .dateRangeList(Collections.emptyList())
        .build();
  }
}
