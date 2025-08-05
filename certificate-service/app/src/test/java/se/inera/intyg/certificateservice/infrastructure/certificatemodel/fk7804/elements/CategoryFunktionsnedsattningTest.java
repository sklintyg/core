package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.CategoryFunktionsnedsattning.categoryFunktionsnedsattning;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class CategoryFunktionsnedsattningTest {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_5");

  @Test
  void shallIncludeId() {
    final var element = categoryFunktionsnedsattning();
    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Funktionsnedsättning")
        .build();

    final var element = categoryFunktionsnedsattning();

    assertEquals(expectedConfiguration, element.elementSpecification(ELEMENT_ID).configuration());
  }
}

