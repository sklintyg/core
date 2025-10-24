package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class CategoryIntellektuellFunktionsnedsattningV2Test {

  @Test
  void shouldIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Intellektuell funktionsnedsättning")
        .build();

    final var element = CategoryIntellektuellFunktionsnedsattningV2.categoryIntellektuellFunktionsnedsattningV2();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shouldIncludeId() {
    final var expectedId = new ElementId("KAT_18");

    final var element = CategoryIntellektuellFunktionsnedsattningV2.categoryIntellektuellFunktionsnedsattningV2();

    assertEquals(expectedId, element.id());
  }
}

