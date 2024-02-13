package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Value
@Builder
public class ElementConfigurationIssuingUnit implements ElementConfiguration {

  @Getter(onMethod = @__(@Override))
  ElementType type = ElementType.ISSUING_UNIT;

  @Override
  public String name() {
    return null;
  }
}
