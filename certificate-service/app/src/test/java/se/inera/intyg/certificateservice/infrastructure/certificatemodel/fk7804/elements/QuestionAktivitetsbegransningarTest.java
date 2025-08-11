package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionAktivitetsbegransningar.QUESTION_AKTIVITETSBEGRANSNING_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionAktivitetsbegransningar.QUESTION_AKTIVITETSBEGRANSNING_ID;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationIcf;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationIcfValue;

class QuestionAktivitetsbegransningarTest {

  @Test
  void shouldIncludeId() {
    final var element = QuestionAktivitetsbegransningar.questionAktivitetsbegransningar();
    assertEquals(QUESTION_AKTIVITETSBEGRANSNING_ID, element.id());
  }

  @Test
  void shouldIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationIcf.builder()
        .id(QUESTION_AKTIVITETSBEGRANSNING_FIELD_ID)
        .name(
            "Ange vilken/vilka funktionsnedsättningar patienten har till följd av sjukdom och om möjligt svårighetsgrad. Ange även vad din bedömning av funktionsnedsättningar baseras på. Beskriv relevanta undersökningsfynd, testresultat, utredningssvar eller andra uppgifter (exempelvis anamnesuppgifter) och hur du bedömer dem.")
        .modalLabel("Välj enbart de svårigheter som påverkar patientens sysselsättning.")
        .collectionsLabel(
            "Svårigheter som påverkar patientens sysselsättning:")
        .placeholder(
            "Hur begränsar ovanstående patientens sysselsättning och i vilken utsträckning?")
        .build();

    final var element = QuestionAktivitetsbegransningar.questionAktivitetsbegransningar();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shouldIncludeRules() {
    final var element = QuestionAktivitetsbegransningar.questionAktivitetsbegransningar();
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(QUESTION_AKTIVITETSBEGRANSNING_ID)
            .type(ElementRuleType.MANDATORY)
            .expression(
                new RuleExpression(
                    "$17.1"
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
    final var element = QuestionAktivitetsbegransningar.questionAktivitetsbegransningar();
    final var expectedValidations = List.of(
        ElementValidationIcfValue.builder()
            .mandatory(true)
            .limit(4000)
            .build()
    );
    assertEquals(expectedValidations, element.validations());
  }

}