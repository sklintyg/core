package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateList;
import se.inera.intyg.certificateservice.domain.common.model.Code;

@Value
@Builder
public class ElementConfigurationCheckboxMultipleDate implements ElementConfiguration {

  @Getter(onMethod = @__(@Override))
  String name;
  @Getter(onMethod = @__(@Override))
  ElementType type = ElementType.CHECKBOX_MULTIPLE_DATE;
  @Getter(onMethod = @__(@Override))
  ElementMessage message;
  List<CheckboxDate> dates;
  FieldId id;

  @Override
  public ElementValue emptyValue() {
    return ElementValueDateList.builder()
        .dateListId(id)
        .dateList(Collections.emptyList())
        .build();
  }

  public Code code(ElementValueDate elementValueDate) {
    return dates.stream()
        .filter(checkboxDate -> checkboxDate.id().equals(elementValueDate.dateId()))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException(
                "Cannot find matching code for dateId '%s'".formatted(elementValueDate.dateId())
            )
        )
        .code();
  }
}
