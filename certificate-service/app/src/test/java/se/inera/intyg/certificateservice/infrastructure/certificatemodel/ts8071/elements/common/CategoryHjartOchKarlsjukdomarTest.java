package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class CategoryHjartOchKarlsjukdomarTest {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_5");

  @Test
  void shallIncludeId() {
    final var element = CategoryHjartOchKarlsjukdomar.categoryHjartOchKarlsjukdomar();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Hjärt- och kärlsjukdomar")
        .build();

    final var element = CategoryHjartOchKarlsjukdomar.categoryHjartOchKarlsjukdomar();

    assertEquals(expectedConfiguration, element.configuration());
  }
}