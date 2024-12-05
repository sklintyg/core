package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class CategoryIdentitetTest {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_0.2");

  @Test
  void shallIncludeId() {
    final var element = CategoryIdentitet.categoryIdentitet();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Identitet")
        .build();

    final var element = CategoryIdentitet.categoryIdentitet();

    assertEquals(expectedConfiguration, element.configuration());
  }
}