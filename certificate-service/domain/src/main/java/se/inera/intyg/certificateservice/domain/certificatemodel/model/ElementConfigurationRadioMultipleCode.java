package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;

@Value
@Builder
public class ElementConfigurationRadioMultipleCode implements ElementConfiguration {

  @Getter(onMethod = @__(@Override))
  String name;
  @Getter(onMethod = @__(@Override))
  ElementType type = ElementType.RADIO_MULTIPLE_CODE;
  FieldId id;
  List<ElementConfigurationCode> list;
  ElementLayout elementLayout;

  @Override
  public ElementValue emptyValue() {
    return ElementValueCode.builder()
        .codeId(id)
        .build();
  }
}
