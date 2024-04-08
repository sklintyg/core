package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;

@Value
@Builder
public class ElementConfigurationTextArea implements ElementConfiguration {

  @Getter(onMethod = @__(@Override))
  String name;
  @Getter(onMethod = @__(@Override))
  ElementType type = ElementType.TEXT_AREA;
  FieldId id;

  @Override
  public ElementValue emptyValue() {
    return ElementValueText.builder()
        .textId(id)
        .build();
  }
}
