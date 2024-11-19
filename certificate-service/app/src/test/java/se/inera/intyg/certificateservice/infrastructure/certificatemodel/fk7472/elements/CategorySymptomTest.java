package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class CategorySymptomTest {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_1");

  @Test
  void shallIncludeId() {
    final var element = CategorySymptom.categorySymptom();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Barnets diagnos eller symtom")
        .build();

    final var element = CategorySymptom.categorySymptom();

    assertEquals(expectedConfiguration, element.configuration());
  }
}