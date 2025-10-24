package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class CategoryEpilepsiTest {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_8");

  @Test
  void shallIncludeId() {
    final var element = CategoryEpilepsi.categoryEpilepsi();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Epilepsi, epileptiskt anfall och annan medvetandestörning")
        .build();

    final var element = CategoryEpilepsi.categoryEpilepsi();

    assertEquals(expectedConfiguration, element.configuration());
  }
}