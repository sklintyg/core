package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class CategoryNeurologiskaSjukdomarTest {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_7");

  @Test
  void shallIncludeId() {
    final var element = CategoryNeurologiskaSjukdomar.categoryNeurologiskaSjukdomar();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Neurologiska sjukdomar")
        .build();

    final var element = CategoryNeurologiskaSjukdomar.categoryNeurologiskaSjukdomar();

    assertEquals(expectedConfiguration, element.configuration());
  }
}