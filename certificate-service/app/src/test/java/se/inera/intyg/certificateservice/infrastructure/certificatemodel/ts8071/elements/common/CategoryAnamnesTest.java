package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;

class CategoryAnamnesTest {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_1.2");

  @Test
  void shallIncludeId() {
    final var element = CategoryAnamnes.categoryAnamnes();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Anamnesfrågor")
        .build();

    final var element = CategoryAnamnes.categoryAnamnes();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shallIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(new ElementId("4"))
            .type(ElementRuleType.SHOW)
            .expression(new RuleExpression("!$4.1 && !empty($4.1)"))
            .build()
    );

    final var element = CategoryAnamnes.categoryAnamnes();

    assertEquals(expectedRules, element.rules());
  }
}