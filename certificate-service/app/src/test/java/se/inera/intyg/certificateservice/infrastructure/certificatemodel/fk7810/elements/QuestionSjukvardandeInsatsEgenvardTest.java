package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType.MANDATORY;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionSjukvardandeInsatsEgenvard.questionSjukvardandeInsatsEgenvard;

import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;

class QuestionSjukvardandeInsatsEgenvardTest {

  private static final ElementId ELEMENT_ID = new ElementId("70.3");

  @Test
  void shallIncludeId() {
    final var element = questionSjukvardandeInsatsEgenvard();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationRadioBoolean.builder()
        .name(
            "Har patienten behov av hjälp som bedöms kunna utföras som egenvård?")
        .id(new FieldId("70.3"))
        .selectedText("Ja")
        .unselectedText("Nej")
        .build();

    final var element = questionSjukvardandeInsatsEgenvard();

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
                    "exists($70.3)"
                )
            )
            .build()
    );

    final var element = questionSjukvardandeInsatsEgenvard();

    assertEquals(expectedRules, element.rules());

  }

  @Test
  void shallIncludeValidations() {
    final var expectedValidations = List.of(
        ElementValidationBoolean.builder()
            .mandatory(true)
            .build()
    );

    final var element = questionSjukvardandeInsatsEgenvard();

    assertEquals(expectedValidations, element.validations());
  }

  @Test
  @Disabled
  void shallIncludePdfConfiguration() {
    final var expected = PdfConfigurationText.builder()
        .pdfFieldId(new PdfFieldId("form1[0].#subform[0].flt_txtAngeAnnat[0]"))
        .maxLength(50)
        .overflowSheetFieldId(new PdfFieldId(("form1[0].#subform[4].flt_txtFortsattningsblad[0]")))
        .build();

    final var element = questionSjukvardandeInsatsEgenvard();

    assertEquals(expected, element.pdfConfiguration());
  }
}
