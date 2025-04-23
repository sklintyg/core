package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

import java.util.List;

class CategoryDiagnosTest {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_2");

  @Test
  void shallIncludeId() {
    final var element = CategoryDiagnos.categoryDiagnos();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Diagnos")
        .build();

    final var element = CategoryDiagnos.categoryDiagnos();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shallIncludeChildren() {
    final var child = ElementSpecification.builder().id(new ElementId("CHILD_1")).build();

    final var element = CategoryDiagnos.categoryDiagnos(child);

    assertEquals(List.of(child), element.children());
  }
}
