package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements;

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

class QuestionMedicinskBehandlingTest {

  private static final ElementId ELEMENT_ID = new ElementId("19");

  @Test
  void shouldIncludeId() {
    final var element = QuestionMedicinskBehandling.questionMedicinskBehandling();
    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shouldIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationTextArea.builder()
        .name(
            "Beskriv pågående och planerade medicinska behandlingar/åtgärder som kan påverka arbetsförmågan")
        .label("Ange vad syftet är och om möjligt tidsplan samt ansvarig vårdenhet.")
        .id(new FieldId("19.1"))
        .build();

    final var element = QuestionMedicinskBehandling.questionMedicinskBehandling();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shouldIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(new ElementId("19"))
            .type(ElementRuleType.MANDATORY)
            .expression(new RuleExpression("$19.1"))
            .build(),
        ElementRuleLimit.builder()
            .id(new ElementId("19"))
            .type(ElementRuleType.TEXT_LIMIT)
            .limit(new RuleLimit((short) 4000))
            .build(),
        ElementRuleExpression.builder()
            .id(new ElementId(("27")))
            .type(ElementRuleType.HIDE)
            .expression(new RuleExpression("$27.1"))
            .build()
    );

    final var element = QuestionMedicinskBehandling.questionMedicinskBehandling();

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shouldIncludeValidations() {
    final var expectedValidations = List.of(
        ElementValidationText.builder()
            .mandatory(true)
            .limit(4000)
            .build()
    );

    final var element = QuestionMedicinskBehandling.questionMedicinskBehandling();

    assertEquals(expectedValidations, element.validations());
  }
}

