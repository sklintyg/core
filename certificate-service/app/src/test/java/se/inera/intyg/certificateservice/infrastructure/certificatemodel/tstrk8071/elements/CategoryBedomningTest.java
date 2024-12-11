package se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class CategoryBedomningTest {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_17");

  @Test
  void shallIncludeId() {
    final var element = CategoryBedomning.categoryBedomning();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Bedömning")
        .build();

    final var element = CategoryBedomning.categoryBedomning();

    assertEquals(expectedConfiguration, element.configuration());
  }
}