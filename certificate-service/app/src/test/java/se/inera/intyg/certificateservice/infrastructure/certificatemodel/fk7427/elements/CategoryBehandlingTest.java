package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class CategoryBehandlingTest {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_5");

  @Test
  void shallIncludeId() {
    final var element = CategoryBehandling.categoryBehandling();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Behandling")
        .build();

    final var element = CategoryBehandling.categoryBehandling();

    assertEquals(expectedConfiguration, element.configuration());
  }
}