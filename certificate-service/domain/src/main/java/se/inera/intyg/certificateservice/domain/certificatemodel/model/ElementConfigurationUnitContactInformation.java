package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Value
@Builder
public class ElementConfigurationUnitContactInformation implements ElementConfiguration {

  @Getter(onMethod = @__(@Override))
  ElementType type = ElementType.ISSUING_UNIT;

  public static final ElementId UNIT_CONTACT_INFORMATION = new ElementId(
      "UNIT_CONTACT_INFORMATION");
}
