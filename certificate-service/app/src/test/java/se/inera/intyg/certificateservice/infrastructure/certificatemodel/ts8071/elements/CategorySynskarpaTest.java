package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class CategorySynskarpaTest {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_1.1");

  @Test
  void shallIncludeId() {
    final var element = CategorySynskarpa.categorySynskarpa();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Synskärpa")
        .build();

    final var element = CategorySynskarpa.categorySynskarpa();

    assertEquals(expectedConfiguration, element.configuration());
  }
}