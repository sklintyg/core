package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class CategoryAnamnesTest {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_1.2");

  @Test
  void shallIncludeId() {
    final var element = CategoryAnamnes.categoryAnamnes();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Anamnesfrågor")
        .build();

    final var element = CategoryAnamnes.categoryAnamnes();

    assertEquals(expectedConfiguration, element.configuration());
  }
}