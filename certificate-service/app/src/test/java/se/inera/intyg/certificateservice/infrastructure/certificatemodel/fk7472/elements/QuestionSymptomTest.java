package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleLimit;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleLimit;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;

class QuestionSymptomTest {

  private static final ElementId ELEMENT_ID = new ElementId("55");

  @Test
  void shallIncludeId() {
    final var element = QuestionSymptom.questionSymptom();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationTextArea.builder()
        .name("Ange diagnos eller symtom")
        .id(new FieldId("55.1"))
        .build();

    final var element = QuestionSymptom.questionSymptom();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shallIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(new ElementId("55"))
            .type(ElementRuleType.MANDATORY)
            .expression(
                new RuleExpression("$55.1")
            )
            .build(),
        ElementRuleLimit.builder()
            .id(new ElementId("55"))
            .type(ElementRuleType.TEXT_LIMIT)
            .limit(new RuleLimit((short) 318))
            .build()
    );

    final var element = QuestionSymptom.questionSymptom();

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shallIncludeValidations() {
    final var expectedValidations = List.of(
        ElementValidationText.builder()
            .mandatory(true)
            .limit(318)
            .build()
    );

    final var element = QuestionSymptom.questionSymptom();

    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shallIncludeWhenRenewingTrue() {
    final var element = QuestionSymptom.questionSymptom();

    assertEquals(Boolean.TRUE, element.includeWhenRenewing());
  }

  @Test
  void shallIncludePdfConfiguration() {
    final var expected = PdfConfigurationText.builder()
        .pdfFieldId(new PdfFieldId("form1[0].#subform[0].flt_txtDiagnos[0]"))
        .build();

    final var element = QuestionSymptom.questionSymptom();

    assertEquals(expected, element.pdfConfiguration());
  }
}