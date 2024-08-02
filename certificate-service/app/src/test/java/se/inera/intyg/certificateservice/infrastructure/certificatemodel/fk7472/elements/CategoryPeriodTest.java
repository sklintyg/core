package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class CategoryPeriodTest {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_2");

  @Test
  void shallIncludeId() {
    final var element = CategoryPeriod.categoryPeriod();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Period som barnet inte bör vårdas i ordinarie tillsynsform")
        .build();

    final var element = CategoryPeriod.categoryPeriod();

    assertEquals(expectedConfiguration, element.configuration());
  }
}