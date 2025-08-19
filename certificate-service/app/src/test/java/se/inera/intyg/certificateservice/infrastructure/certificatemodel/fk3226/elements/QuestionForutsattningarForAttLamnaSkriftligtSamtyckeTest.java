package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;

class QuestionForutsattningarForAttLamnaSkriftligtSamtyckeTest {

  private static final ElementId ELEMENT_ID = new ElementId("53");

  @Test
  void shallIncludeId() {
    final var element = QuestionForutsattningarForAttLamnaSkriftligtSamtycke.questionForutsattningarForAttLamnaSkriftligtSamtycke();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationRadioBoolean.builder()
        .id(new FieldId("53.1"))
        .name(
            "Har patienten de medicinska förutsättningarna för att kunna lämna samtycke?")
        .selectedText("Ja")
        .unselectedText("Nej")
        .build();

    final var element = QuestionForutsattningarForAttLamnaSkriftligtSamtycke.questionForutsattningarForAttLamnaSkriftligtSamtycke();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shallIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.MANDATORY)
            .expression(
                new RuleExpression(
                    "exists($53.1)"
                )
            )
            .build()
    );

    final var element = QuestionForutsattningarForAttLamnaSkriftligtSamtycke.questionForutsattningarForAttLamnaSkriftligtSamtycke();

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shallIncludeValidations() {
    final var expectedValidations = List.of(
        ElementValidationBoolean.builder()
            .mandatory(true)
            .build()
    );

    final var element = QuestionForutsattningarForAttLamnaSkriftligtSamtycke.questionForutsattningarForAttLamnaSkriftligtSamtycke();

    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shallIncludePdfConfiguration() {
    final var expected = PdfConfigurationBoolean.builder()
        .checkboxTrue(new PdfFieldId("form1[0].#subform[1].ksr_Ja_Modul3[0]"))
        .checkboxFalse(new PdfFieldId("form1[0].#subform[1].ksr_Nej_Modul3[0]"))
        .build();

    final var element = QuestionForutsattningarForAttLamnaSkriftligtSamtycke.questionForutsattningarForAttLamnaSkriftligtSamtycke();

    assertEquals(expected, element.pdfConfiguration());
  }

  @Test
  void shouldHaveIncludeWhenRenewingFalse() {
    final var element = QuestionForutsattningarForAttLamnaSkriftligtSamtycke.questionForutsattningarForAttLamnaSkriftligtSamtycke();
    assertFalse(element.includeWhenRenewing());
  }
}