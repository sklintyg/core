package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvIntygetGallerFor.FORLANG_GR_II_III;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvIntygetGallerFor.GR_II_III;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvIntygetGallerFor.TAXI;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionIntygetAvser.QUESTION_INTYGET_AVSER_ID;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;

class CategoryHorselV2Test {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_3");

  @Test
  void shouldIncludeId() {
    final var element = CategoryHorselV2.categoryHorselV2();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shouldIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Hörsel")
        .build();

    final var element = CategoryHorselV2.categoryHorselV2();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shouldIncludeRules() {
    final var expectedRule = ElementRuleExpression.builder()
        .id(QUESTION_INTYGET_AVSER_ID)
        .type(ElementRuleType.SHOW)
        .expression(
            new RuleExpression(
                "exists($%s) || exists($%s) || exists($%s)".formatted(
                    GR_II_III.code(),
                    FORLANG_GR_II_III.code(),
                    TAXI.code()
                )
            )
        )
        .build();

    final var element = CategoryHorselV2.categoryHorselV2();

    assertEquals(List.of(expectedRule), element.rules());
  }

  @Test
  void shouldIncludeChildren() {
    final var expectedChild = CategoryHorselV2.categoryHorselV2();
    final var element = CategoryHorselV2.categoryHorselV2(expectedChild);

    assertEquals(expectedChild, element.children().getFirst());
  }
}

