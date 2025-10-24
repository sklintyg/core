package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class CategoryDiabetesTest {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_6");

  @Test
  void shallIncludeId() {
    final var element = CategoryDiabetes.categoryDiabetes();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Diabetes")
        .build();

    final var element = CategoryDiabetes.categoryDiabetes();

    assertEquals(expectedConfiguration, element.configuration());
  }
}