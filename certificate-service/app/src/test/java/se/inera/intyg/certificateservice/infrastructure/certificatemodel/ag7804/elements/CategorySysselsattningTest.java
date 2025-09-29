package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.CategorySysselsattning.categorySysselsattning;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class CategorySysselsattningTest {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_3");

  @Test
  void shallIncludeId() {
    final var element = categorySysselsattning();
    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Sysselsättning")
        .build();

    final var element = categorySysselsattning();

    assertEquals(expectedConfiguration, element.elementSpecification(ELEMENT_ID).configuration());
  }

//  @Test
//  void shouldIncludeHideRule() {
//    final var element = categorySysselsattning();
//    final var expectedHideRule = ElementRuleExpression.builder()
//        .id(QUESTION_SMITTBARARPENNING_ID)
//        .type(ElementRuleType.HIDE)
//        .expression(new RuleExpression("$" + QUESTION_SMITTBARARPENNING_FIELD_ID.value()))
//        .build();
//    assertTrue(element.rules().contains(expectedHideRule));
//  }

}