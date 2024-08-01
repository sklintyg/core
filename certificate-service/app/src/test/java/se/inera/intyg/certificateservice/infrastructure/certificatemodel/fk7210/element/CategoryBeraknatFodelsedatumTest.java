package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7210.element;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7210.elements.CategoryBeraknatFodelsedatum;

class CategoryBeraknatFodelsedatumTest {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_1");

  @Test
  void shallIncludeId() {
    final var element = CategoryBeraknatFodelsedatum.categoryBeraknatFodelsedatum();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Beräknat födelsedatum")
        .build();

    final var element = CategoryBeraknatFodelsedatum.categoryBeraknatFodelsedatum();

    assertEquals(expectedConfiguration, element.configuration());
  }
}