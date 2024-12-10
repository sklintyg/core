package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.time.Period;
import java.time.format.DateTimeFormatter;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementSimplifiedValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;

@Value
@Builder
public class ElementConfigurationDate implements ElementConfiguration {

  @Getter(onMethod = @__(@Override))
  String name;
  @Getter(onMethod = @__(@Override))
  ElementType type = ElementType.DATE;
  @Getter(onMethod = @__(@Override))
  ElementMessage message;
  FieldId id;
  Period min;
  Period max;

  @Override
  public ElementValue emptyValue() {
    return ElementValueDate.builder()
        .dateId(id)
        .build();
  }

  @Override
  public ElementSimplifiedValue simplified(ElementValue value) {
    if (!(value instanceof ElementValueDate elementValueDate)) {
      throw new IllegalStateException("Wrong value type");
    }

    return ElementSimplifiedValueText.builder()
        .text(elementValueDate.date().format(DateTimeFormatter.ISO_DATE))
        .build();
  }
}
