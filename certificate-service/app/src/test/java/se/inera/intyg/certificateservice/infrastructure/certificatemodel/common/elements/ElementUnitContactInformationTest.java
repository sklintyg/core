package se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationUnitContactInformation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationUnitContactInformation;

class ElementUnitContactInformationTest {

  private static final ElementId ELEMENT_ID = new ElementId("UNIT_CONTACT_INFORMATION");

  @Test
  void shallIncludeId() {
    final var element = ElementUnitContactInformation.issuingUnitContactInfo();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationUnitContactInformation.builder()
        .build();

    final var element = ElementUnitContactInformation.issuingUnitContactInfo();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shallIncludeValidation() {
    final var expectedValidation = List.of(
        ElementValidationUnitContactInformation.builder().build()
    );

    final var element = ElementUnitContactInformation.issuingUnitContactInfo();

    assertEquals(expectedValidation, element.validations());
  }
}