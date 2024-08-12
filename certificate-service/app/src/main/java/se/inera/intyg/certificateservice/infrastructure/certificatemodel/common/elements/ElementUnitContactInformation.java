package se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.elements;

import static se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationUnitContactInformation.UNIT_CONTACT_INFORMATION;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationUnitContactInformation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationUnitContactInformation;

public class ElementUnitContactInformation {

  private ElementUnitContactInformation() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification issuingUnitContactInfo() {
    return ElementSpecification.builder()
        .id(UNIT_CONTACT_INFORMATION)
        .configuration(
            ElementConfigurationUnitContactInformation.builder().build()
        )
        .validations(
            List.of(
                ElementValidationUnitContactInformation.builder().build()
            )
        )
        .build();
  }
}
