package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class CategoryNjursjukdomTest {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_9");

  @Test
  void shallIncludeId() {
    final var element = CategoryNjursjukdomar.categoryNjursjukdomar();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Allvarlig nedsatt njurfunktion och njursjukdomar")
        .build();

    final var element = CategoryNjursjukdomar.categoryNjursjukdomar();

    assertEquals(expectedConfiguration, element.configuration());
  }
}