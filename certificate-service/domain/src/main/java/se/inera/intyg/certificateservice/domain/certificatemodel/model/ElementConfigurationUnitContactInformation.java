package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueUnitContactInformation;

@Value
@Builder
public class ElementConfigurationUnitContactInformation implements ElementConfiguration {

  @Getter(onMethod = @__(@Override))
  ElementType type = ElementType.ISSUING_UNIT;

  public static final ElementId UNIT_CONTACT_INFORMATION = new ElementId(
      "UNIT_CONTACT_INFORMATION");

  @Override
  public ElementValue emptyValue() {
    return ElementValueUnitContactInformation.builder().build();
  }
}
