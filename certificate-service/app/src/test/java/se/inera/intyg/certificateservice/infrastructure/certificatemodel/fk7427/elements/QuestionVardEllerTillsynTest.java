package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.FK7427PdfSpecification.ROW_MAX_LENGTH;

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
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;

class QuestionVardEllerTillsynTest {

  private static final ElementId ELEMENT_ID = new ElementId("62");

  @Test
  void shallIncludeId() {
    final var element = QuestionVardEllerTillsyn.questionVardEllerTillsyn();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationTextArea.builder()
        .name("Beskriv barnets behov av vård eller tillsyn")
        .description(
            "Beskriv vilken vård eller tillsyn som barnet behöver av förälder samt omfattning av denna vård eller tillsyn.")
        .id(new FieldId("62.5"))
        .build();

    final var element = QuestionVardEllerTillsyn.questionVardEllerTillsyn();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shallIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(new ElementId("62"))
            .type(ElementRuleType.MANDATORY)
            .expression(new RuleExpression("$62.5"))
            .build(),
        ElementRuleLimit.builder()
            .id(new ElementId("62"))
            .type(ElementRuleType.TEXT_LIMIT)
            .limit(new RuleLimit((short) 4000))
            .build()
    );

    final var element = QuestionVardEllerTillsyn.questionVardEllerTillsyn();

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

    final var element = QuestionVardEllerTillsyn.questionVardEllerTillsyn();

    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shallIncludePdfConfiguration() {
    final var expected = PdfConfigurationText.builder()
        .pdfFieldId(new PdfFieldId("form1[0].#subform[2].flt_txtVilkenVardTillsyn[0]"))
        .maxLength(ROW_MAX_LENGTH * 8)
        .overflowSheetFieldId(
            new PdfFieldId("form1[0].#subform[3].flt_txtFortsattningsblad[0]"))
        .build();

    final var element = QuestionVardEllerTillsyn.questionVardEllerTillsyn();

    assertEquals(expected, element.pdfConfiguration());
  }

}
