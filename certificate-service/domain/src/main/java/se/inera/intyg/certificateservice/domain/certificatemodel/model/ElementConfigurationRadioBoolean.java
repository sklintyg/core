package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;

@Value
@Builder
public class ElementConfigurationRadioBoolean implements ElementConfiguration {

  @Getter(onMethod = @__(@Override))
  String name;
  @Getter(onMethod = @__(@Override))
  ElementType type = ElementType.RADIO_BOOLEAN;
  FieldId id;
  String selectedText;
  String unselectedText;

  @Override
  public ElementValue emptyValue() {
    return ElementValueBoolean.builder()
        .booleanId(id)
        .build();
  }
}
