package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Value
@Builder
public class ElementConfigurationCategory implements ElementConfiguration {

  @Getter(onMethod = @__(@Override))
  String name;
  @Getter(onMethod = @__(@Override))
  ElementType type = ElementType.CATEGORY;
}
