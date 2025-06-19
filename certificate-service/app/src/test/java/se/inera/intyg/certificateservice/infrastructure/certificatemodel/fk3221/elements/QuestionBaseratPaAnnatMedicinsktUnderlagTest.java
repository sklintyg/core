package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221.elements.QuestionBaseratPaAnnatMedicinsktUnderlag.questionBaseratPaAnnatMedicinsktUnderlag;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfRadioOption;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;

class QuestionBaseratPaAnnatMedicinsktUnderlagTest {

  private static final ElementId ELEMENT_ID = new ElementId("3");

  @Test
  void shallIncludeId() {
    final var element = questionBaseratPaAnnatMedicinsktUnderlag();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationRadioBoolean.builder()
        .name(
            "Är utlåtandet även baserat på andra medicinska utredningar eller underlag?")
        .id(new FieldId("3.1"))
        .selectedText("Ja")
        .unselectedText("Nej")
        .build();

    final var element = questionBaseratPaAnnatMedicinsktUnderlag();

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
                    "exists($3.1)"
                )
            )
            .build()
    );

    final var element = questionBaseratPaAnnatMedicinsktUnderlag();

    assertEquals(expectedRule, element.rules());
  }

  @Test
  void shallIncludeValidation() {
    final var expectedValidations = List.of(
        ElementValidationBoolean.builder()
            .mandatory(true)
            .build()
    );

    final var element = questionBaseratPaAnnatMedicinsktUnderlag();

    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shallIncludePdfConfiguration() {
    final var expected = PdfConfigurationRadioBoolean.builder()
        .pdfFieldId(new PdfFieldId("form1[0].#subform[0].RadioButtonList2[0]"))
        .optionTrue(new PdfRadioOption("2"))
        .optionFalse(new PdfRadioOption("1"))
        .build();

    final var element = questionBaseratPaAnnatMedicinsktUnderlag();

    assertEquals(expected, element.pdfConfiguration());
  }
}
