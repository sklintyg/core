package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class CategoryAlkoholNarkotikaOchLakemedelTest {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_12");

  @Test
  void shallIncludeId() {
    final var element = CategoryAlkoholNarkotikaOchLakemedel.categoryAlkoholNarkotikaOchLakemedel();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Alkohol, narkotika och läkemedel")
        .build();

    final var element = CategoryAlkoholNarkotikaOchLakemedel.categoryAlkoholNarkotikaOchLakemedel();

    assertEquals(expectedConfiguration, element.configuration());
  }
}