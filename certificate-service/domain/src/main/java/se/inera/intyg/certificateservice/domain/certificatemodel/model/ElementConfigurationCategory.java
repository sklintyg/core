package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;

@Value
@Builder
public class ElementConfigurationCategory implements ElementConfiguration {

  @Getter(onMethod = @__(@Override))
  String name;
  @Getter(onMethod = @__(@Override))
  ElementType type = ElementType.CATEGORY;
  @Getter(onMethod = @__(@Override))
  String description;
  @Getter(onMethod = @__(@Override))
  ElementMessage message;


  @Override
  public ElementValue emptyValue() {
    return null;
  }

  @Override

  public FieldId id() {
    return null;
  }
}
