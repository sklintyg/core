package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class CategoryDemensOchAndraKognitivaStorningarTest {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_10");

  @Test
  void shallIncludeId() {
    final var element = CategoryDemensOchAndraKognitivaStorningar.categoryDemensOchAndraKognitivaStorningar();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Demens och andra kognitiva störningar")
        .build();

    final var element = CategoryDemensOchAndraKognitivaStorningar.categoryDemensOchAndraKognitivaStorningar();

    assertEquals(expectedConfiguration, element.configuration());
  }
}