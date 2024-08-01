package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7210.element;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;
import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDate;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7210.elements.QuestionBeraknatFodelsedatum;

class QuestionBeraknatFodelsedatumTest {

  private static final ElementId ELEMENT_ID = new ElementId("54");

  @Test
  void shallIncludeId() {
    final var element = QuestionBeraknatFodelsedatum.questionBeraknatFodelsedatum();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationDate.builder()
        .name("Datum")
        .id(new FieldId("54.1"))
        .min(Period.ofDays(0))
        .max(Period.ofYears(1))
        .build();

    final var element = QuestionBeraknatFodelsedatum.questionBeraknatFodelsedatum();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shallIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(new ElementId("54"))
            .type(ElementRuleType.MANDATORY)
            .expression(
                new RuleExpression("$54.1")
            )
            .build()
    );

    final var element = QuestionBeraknatFodelsedatum.questionBeraknatFodelsedatum();

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shallIncludeValidations() {
    final var expectedValidations = List.of(
        ElementValidationDate.builder()
            .mandatory(true)
            .min(Period.ofDays(0))
            .max(Period.ofYears(1))
            .build()
    );

    final var element = QuestionBeraknatFodelsedatum.questionBeraknatFodelsedatum();

    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shallIncludePdfConfiguration() {
    final var expected = PdfConfigurationDate.builder()
        .pdfFieldId(new PdfFieldId("form1[0].#subform[0].flt_dat[0]"))
        .build();

    final var element = QuestionBeraknatFodelsedatum.questionBeraknatFodelsedatum();

    assertEquals(expected, element.pdfConfiguration());
  }
}