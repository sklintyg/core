package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType.MANDATORY;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionSjukvardandeInsatsHSL.questionSjukvardandeInsatsHSL;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfRadioOption;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;

class QuestionSjukvardandeInsatsHSLTest {

  private static final ElementId ELEMENT_ID = new ElementId("70");

  @Test
  void shallIncludeId() {
    final var element = questionSjukvardandeInsatsHSL();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationRadioBoolean.builder()
        .name(
            "Har patienten behov av hjälp som innefattar sjukvårdande insatser enligt HSL?")
        .id(new FieldId("70.1"))
        .selectedText("Ja")
        .unselectedText("Nej")
        .build();

    final var element = questionSjukvardandeInsatsHSL();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shallIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(ELEMENT_ID)
            .type(MANDATORY)
            .expression(
                new RuleExpression(
                    "exists($70.1)"
                )
            )
            .build()
    );

    final var element = questionSjukvardandeInsatsHSL();

    assertEquals(expectedRules, element.rules());

  }

  @Test
  void shallIncludeValidations() {
    final var expectedValidations = List.of(
        ElementValidationBoolean.builder()
            .mandatory(true)
            .build()
    );

    final var element = questionSjukvardandeInsatsHSL();

    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shallIncludePdfConfiguration() {
    final var expected = PdfConfigurationRadioBoolean.builder()
        .pdfFieldId(new PdfFieldId("form1[0].#subform[5].RadioButtonListModul8_1[0]"))
        .optionTrue(new PdfRadioOption("1"))
        .optionFalse(new PdfRadioOption("2"))
        .build();

    final var element = questionSjukvardandeInsatsHSL();

    assertEquals(expected, element.pdfConfiguration());
  }
}