package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextField;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleLimit;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleLimit;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;

class QuestionVardenhetOchTidplanTest {

  private static final ElementId ELEMENT_ID = new ElementId("50.2");

  @Test
  void shallIncludeId() {
    final var element = QuestionVardenhetOchTidplan.questionVardenhetOchTidplan();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationTextField.builder()
        .name("Ange ansvarig vårdenhet och tidplan")
        .label(null)
        .description(null)
        .id(new FieldId("50.2"))
        .build();

    final var element = QuestionVardenhetOchTidplan.questionVardenhetOchTidplan();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shallIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(new ElementId("50.2"))
            .type(ElementRuleType.MANDATORY)
            .expression(new RuleExpression("$50.2"))
            .build(),
        ElementRuleLimit.builder()
            .id(new ElementId("50.2"))
            .type(ElementRuleType.TEXT_LIMIT)
            .limit(new RuleLimit((short) 4000))
            .build(),
        ElementRuleExpression.builder()
            .id(new ElementId("50"))
            .type(ElementRuleType.SHOW)
            .expression(new RuleExpression("$50.1"))
            .build()
    );

    final var element = QuestionVardenhetOchTidplan.questionVardenhetOchTidplan();

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

    final var element = QuestionVardenhetOchTidplan.questionVardenhetOchTidplan();

    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shallIncludeMapping() {
    final var expectedMapping = new ElementMapping(new ElementId("50"), null);
    final var element = QuestionVardenhetOchTidplan.questionVardenhetOchTidplan();
    assertEquals(expectedMapping, element.mapping());
  }

  @Test
  void shallIncludePdfConfiguration() {
    final var expected = PdfConfigurationText.builder()
        .pdfFieldId(new PdfFieldId("form1[0].#subform[2].flt_txtVardenhetTidplan[0]"))
        .maxLength(53)
        .overflowSheetFieldId(new PdfFieldId(("form1[0].#subform[4].flt_txtFortsattningsblad[0]")))
        .build();

    final var element = QuestionVardenhetOchTidplan.questionVardenhetOchTidplan();

    assertEquals(expected, element.pdfConfiguration());
  }
}