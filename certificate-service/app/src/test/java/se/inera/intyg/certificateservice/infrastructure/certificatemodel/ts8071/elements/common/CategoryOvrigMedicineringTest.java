package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class CategoryOvrigMedicineringTest {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_15");

  @Test
  void shallIncludeId() {
    final var element = CategoryOvrigMedicinering.categoryOvrigMedicinering();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Övrig medicinering")
        .build();

    final var element = CategoryOvrigMedicinering.categoryOvrigMedicinering();

    assertEquals(expectedConfiguration, element.configuration());
  }
}