package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;

class CategoryHorselV1Test {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_3");

  @Test
  void shallIncludeId() {
    final var element = CategoryHorselV1.categoryHorselV1();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Hörsel")
        .build();

    final var element = CategoryHorselV1.categoryHorselV1();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shallIncludeRules() {
    final var expected = List.of(
        ElementRuleExpression.builder()
            .id(new ElementId("1"))
            .type(ElementRuleType.SHOW)
            .expression(new RuleExpression(
                "exists($gr_II_III) || exists($tax_leg) || exists($utbyt_utl_kk) || exists($forlang_gr_II_III) || exists($int_begar_ts)"))
            .build()
    );

    final var element = CategoryHorselV1.categoryHorselV1();

    assertEquals(expected, element.rules());
  }
}