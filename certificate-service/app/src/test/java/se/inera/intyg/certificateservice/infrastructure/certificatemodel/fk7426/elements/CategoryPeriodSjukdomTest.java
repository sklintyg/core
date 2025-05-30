package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryPeriodSjukdomTest {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_6");

  @Test
  void shallIncludeId() {
    final var element = CategoryPeriodSjukdom.categoryPeriodSjukdom();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Period för sjukdom")
        .build();

    final var element = CategoryPeriodSjukdom.categoryPeriodSjukdom();

    assertEquals(expectedConfiguration, element.configuration());
  }
}
