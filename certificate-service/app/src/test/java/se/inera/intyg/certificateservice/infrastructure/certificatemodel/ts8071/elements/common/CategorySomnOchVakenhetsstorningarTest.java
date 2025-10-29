package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class CategorySomnOchVakenhetsstorningarTest {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_11");

  @Test
  void shallIncludeId() {
    final var element = CategorySomnOchVakenhetsstorningar.categorySomnOchVakenhetsstorningar();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Sömn- och vakenhetsstörningar")
        .build();

    final var element = CategorySomnOchVakenhetsstorningar.categorySomnOchVakenhetsstorningar();

    assertEquals(expectedConfiguration, element.configuration());
  }
}