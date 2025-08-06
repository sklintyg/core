package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.CategoryAtgarderSomKanFramjaAttergang.categoryAtgarderSomKanFramjaAttergang;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionSmittbararpenning.QUESTION_SMITTBARARPENNING_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionSmittbararpenning.QUESTION_SMITTBARARPENNING_ID;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;

class CategoryAtgarderSomKanFramjaAttergangTest {

  private static final ElementId ELEMENT_ID = new ElementId("KAT_10");

  @Test
  void shallIncludeId() {
    final var element = categoryAtgarderSomKanFramjaAttergang();
    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCategory.builder()
        .name("Åtgärder som kan främja återgången i arbete")
        .build();

    final var element = categoryAtgarderSomKanFramjaAttergang();

    assertEquals(expectedConfiguration, element.elementSpecification(ELEMENT_ID).configuration());
  }

  @Test
  void shallIncludeRules() {
    final var element = categoryAtgarderSomKanFramjaAttergang();
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(QUESTION_SMITTBARARPENNING_ID)
            .type(ElementRuleType.HIDE)
            .expression(new RuleExpression("$" + QUESTION_SMITTBARARPENNING_FIELD_ID.value()))
            .build()
    );
    assertEquals(expectedRules, element.rules());
  }
}
