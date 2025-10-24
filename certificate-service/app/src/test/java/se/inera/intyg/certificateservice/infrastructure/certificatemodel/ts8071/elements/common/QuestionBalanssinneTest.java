package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;

class QuestionBalanssinneTest {

  private static final ElementId ELEMENT_ID = new ElementId("8");

  @Test
  void shallIncludeId() {
    final var element = QuestionBalanssinne.questionBalanssinne();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationRadioBoolean.builder()
        .name(
            "Har personen överraskande anfall av balansrubbningar eller yrsel som kan innebära en trafiksäkerhetsrisk?")
        .description(
            "Här avses överraskande anfall av balansrubbningar eller yrsel som inträffat nyligen och krävt läkarkontakt, exempelvis vid sjukdomen Morbus Menière. Balansrubbningar eller yrsel som beror på till exempel godartad lägesyrsel (kristallsjuka), lågt blodtryck eller migrän "
                + "behöver inte anges.")
        .id(new FieldId("8.1"))
        .selectedText("Ja")
        .unselectedText("Nej")
        .build();

    final var element = QuestionBalanssinne.questionBalanssinne();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shallIncludeRules() {
    final var expectedRule = List.of(
        ElementRuleExpression.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.MANDATORY)
            .expression(
                new RuleExpression(
                    "exists($8.1)"
                )
            )
            .build()
    );

    final var element = QuestionBalanssinne.questionBalanssinne();

    assertEquals(expectedRule, element.rules());
  }

  @Test
  void shallIncludeValidation() {
    final var expectedValidations = List.of(
        ElementValidationBoolean.builder()
            .mandatory(true)
            .build()
    );

    final var element = QuestionBalanssinne.questionBalanssinne();

    assertEquals(expectedValidations, element.validations());
  }
}