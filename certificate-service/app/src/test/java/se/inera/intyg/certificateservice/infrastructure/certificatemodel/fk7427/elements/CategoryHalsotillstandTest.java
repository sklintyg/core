package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class CategoryHalsotillstandTest {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_3");

  @Test
  void shallIncludeId() {
    final var element = CategoryHalsotillstand.categoryHalsotillstand();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Barnets hälsotillstånd")
        .build();

    final var element = CategoryHalsotillstand.categoryHalsotillstand();

    assertEquals(expectedConfiguration, element.configuration());
  }

}