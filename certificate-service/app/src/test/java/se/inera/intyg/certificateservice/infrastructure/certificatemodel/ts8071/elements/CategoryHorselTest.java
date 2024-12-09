package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;

class CategoryHorselTest {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_3");

  @Test
  void shallIncludeId() {
    final var element = CategoryHorsel.categoryHorsel();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Hörsel")
        .build();

    final var element = CategoryHorsel.categoryHorsel();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shallIncludeRules() {
    final var expected = List.of(
        ElementRuleExpression.builder()
            .id(new ElementId("1"))
            .type(ElementRuleType.SHOW)
            .expression(new RuleExpression("$gr_II_III || $tax_leg"))
            .build()
    );

    final var element = CategoryHorsel.categoryHorsel();

    assertEquals(expected, element.rules());
  }
}