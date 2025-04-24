package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

import java.util.List;

class CategoryGrundForBedomningTest {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_4");

  @Test
  void shallIncludeId() {
    final var element = CategoryGrundForBedomning.categoryGrundForBedomning();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Grund för bedömning")
        .build();

    final var element = CategoryGrundForBedomning.categoryGrundForBedomning();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shallIncludeChildren() {
    final var child = ElementSpecification.builder().id(new ElementId("CHILD_1")).build();

    final var element = CategoryGrundForBedomning.categoryGrundForBedomning(child);

    assertEquals(List.of(child), element.children());
  }
}
