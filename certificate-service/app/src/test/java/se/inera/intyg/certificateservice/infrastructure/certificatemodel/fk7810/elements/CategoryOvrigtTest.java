package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.CategoryOvrigt.categoryOvrigt;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class CategoryOvrigtTest {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_9");

  @Test
  void shallIncludeId() {
    final var element = categoryOvrigt();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Övrigt")
        .build();

    final var element = categoryOvrigt();

    assertEquals(expectedConfiguration, element.configuration());
  }
}