package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionFunktionsnedsattningar.QUESTION_FUNKTIONSNEDSATTNINGAR_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionFunktionsnedsattningar.QUESTION_FUNKTIONSNEDSATTNINGAR_ID;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationIcf;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationIcfValue;

class QuestionFunktionsnedsattningarTest {

  @Test
  void shouldIncludeId() {
    final var element = QuestionFunktionsnedsattningar.questionFunktionsnedsattningar();
    assertEquals(QUESTION_FUNKTIONSNEDSATTNINGAR_ID, element.id());
  }

  @Test
  void shouldIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationIcf.builder()
        .id(QUESTION_FUNKTIONSNEDSATTNINGAR_FIELD_ID)
        .name(
            "Ange vilken/vilka funktionsnedsättningar patienten har till följd av sjukdom och om möjligt svårighetsgrad. Ange även vad din bedömning av funktionsnedsättningar baseras på. Beskriv relevanta undersökningsfynd, testresultat, utredningssvar eller andra uppgifter (exempelvis anamnesuppgifter) och hur du bedömer dem.")
        .modalLabel("Välj enbart de problem som påverkar patienten.")
        .collectionsLabel(
            "Problem som påverkar patientens möjlighet att utföra sin sysselsättning:")
        .placeholder(
            "Vad grundar sig bedömningen på? På vilka sätt och i vilken utsträckning är patienten påverkad?")
        .build();

    final var element = QuestionFunktionsnedsattningar.questionFunktionsnedsattningar();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shouldIncludeRules() {
    final var element = QuestionFunktionsnedsattningar.questionFunktionsnedsattningar();
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(QUESTION_FUNKTIONSNEDSATTNINGAR_ID)
            .type(ElementRuleType.MANDATORY)
            .expression(
                new RuleExpression(
                    "$35.1"
                )
            )
            .build(),
        ElementRuleExpression.builder()
            .id(new ElementId("27"))
            .type(ElementRuleType.HIDE)
            .expression(new RuleExpression("$27.1"))
            .build()
    );
    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shouldIncludeValidation() {
    final var element = QuestionFunktionsnedsattningar.questionFunktionsnedsattningar();
    final var expectedValidations = List.of(
        ElementValidationIcfValue.builder()
            .mandatory(true)
            .limit(4000)
            .build()
    );
    assertEquals(expectedValidations, element.validations());
  }
}