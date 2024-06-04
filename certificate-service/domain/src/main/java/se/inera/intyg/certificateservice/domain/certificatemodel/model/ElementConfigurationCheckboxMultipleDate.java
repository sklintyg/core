package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateList;

@Value
@Builder
public class ElementConfigurationCheckboxMultipleDate implements ElementConfiguration {

  @Getter(onMethod = @__(@Override))
  String name;
  @Getter(onMethod = @__(@Override))
  ElementType type = ElementType.CHECKBOX_MULTIPLE_DATE;
  List<CheckboxDate> dates;
  FieldId id;

  @Override
  public ElementValue emptyValue() {
    return ElementValueDateList.builder()
        .dateList(Collections.emptyList())
        .build();
  }
}
