package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class CategoryGrundForMedicinsktUnderlagTest {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_1");

  @Test
  void shallIncludeId() {
    final var element = se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements.CategoryGrundForMedicinsktUnderlag.categoryGrundForMedicinsktUnderlag();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Grund för medicinskt underlag")
        .build();

    final var element = CategoryGrundForMedicinsktUnderlag.categoryGrundForMedicinsktUnderlag();

    assertEquals(expectedConfiguration, element.elementSpecification(ELEMENT_ID).configuration());
  }
}