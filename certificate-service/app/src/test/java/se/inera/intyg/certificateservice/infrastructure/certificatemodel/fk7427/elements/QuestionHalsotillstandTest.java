package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleLimit;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleLimit;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;

class QuestionHalsotillstandTest {

  private static final ElementId ELEMENT_ID = new ElementId("59");

  @Test
  void shallIncludeId() {
    final var element = QuestionHalsotillstand.questionHalsotillstand();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationTextArea.builder()
        .name("Beskriv barnets aktuella hälsotillstånd")
        .description(
            "Beskriv barnets nuvarande hälsotillstånd (utifrån akut sjukdom eller försämring i funktionsnedsättning).")
        .id(new FieldId("59.3"))
        .build();

    final var element = QuestionHalsotillstand.questionHalsotillstand();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shallIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(new ElementId("59"))
            .type(ElementRuleType.MANDATORY)
            .expression(new RuleExpression("$59.3"))
            .build(),
        ElementRuleLimit.builder()
            .id(new ElementId("59"))
            .type(ElementRuleType.TEXT_LIMIT)
            .limit(new RuleLimit((short) 4000))
            .build()
    );

    final var element = QuestionHalsotillstand.questionHalsotillstand();

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shallIncludeValidations() {
    final var expectedValidations = List.of(
        ElementValidationText.builder()
            .mandatory(true)
            .limit(4000)
            .build()
    );

    final var element = QuestionHalsotillstand.questionHalsotillstand();

    assertEquals(expectedValidations, element.validations());
  }

}