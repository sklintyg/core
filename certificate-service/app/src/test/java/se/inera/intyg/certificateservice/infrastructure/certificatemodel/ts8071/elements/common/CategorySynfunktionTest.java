package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class CategorySynfunktionTest {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_1.0");

  @Test
  void shallIncludeId() {
    final var element = CategorySynfunktion.categorySynfunktion();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Synfunktioner")
        .build();

    final var element = CategorySynfunktion.categorySynfunktion();

    assertEquals(expectedConfiguration, element.configuration());
  }
}