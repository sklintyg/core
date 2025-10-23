package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class CategoryAlkoholOchLakemedelV2Test {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_12");

  @Test
  void shouldIncludeId() {
    final var element = CategoryAlkoholOchLakemedelV2.categoryAlkoholOchLakemedelV2();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shouldIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Alkohol, andra psykoaktiva substanser och läkemedel")
        .build();

    final var element = CategoryAlkoholOchLakemedelV2.categoryAlkoholOchLakemedelV2();

    assertEquals(expectedConfiguration, element.configuration());
  }

}