package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRange;

@Value
@Builder
public class ElementConfigurationDateRange implements ElementConfiguration {

  @Getter(onMethod = @__(@Override))
  String name;
  @Getter(onMethod = @__(@Override))
  ElementType type = ElementType.DATE_RANGE;
  @Getter(onMethod = @__(@Override))
  ElementMessage message;
  FieldId id;
  String labelFrom;
  String labelTo;

  @Override
  public ElementValue emptyValue() {
    return ElementValueDateRange.builder()
        .id(id)
        .build();
  }
}
