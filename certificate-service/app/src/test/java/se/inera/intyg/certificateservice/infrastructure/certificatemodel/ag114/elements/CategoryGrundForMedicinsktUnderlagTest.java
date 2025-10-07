package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.CategoryGrundForMedicinsktUnderlag.categoryGrundForMedicinsktUnderlag;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class CategoryGrundForMedicinsktUnderlagTest {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_1");

  @Test
  void shouldIncludeId() {
    final var element = categoryGrundForMedicinsktUnderlag();
    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shouldIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Grund för medicinskt underlag")
        .build();

    final var element = categoryGrundForMedicinsktUnderlag();

    assertEquals(expectedConfiguration, element.elementSpecification(ELEMENT_ID).configuration());
  }
}
