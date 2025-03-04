package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;
import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxDateRangeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationDateRangeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDateRangeList;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0008;

class QuestionPeriodTest {

  private static final ElementId ELEMENT_ID = new ElementId("56");

  @Test
  void shallIncludeId() {
    final var element = QuestionPeriod.questionPeriod();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCheckboxDateRangeList.builder()
        .name("Jag bedömer att barnet inte ska vårdas i ordinarie tillsynsform")
        .label("Andel av ordinarie tid:")
        .id(new FieldId("56.1"))
        .hideWorkingHours(true)
        .min(Period.ofDays(-90))
        .dateRanges(
            List.of(
                new ElementConfigurationCode(
                    new FieldId("EN_ATTONDEL"),
                    "12,5 procent",
                    CodeSystemKvFkmu0008.EN_ATTONDEL
                ),
                new ElementConfigurationCode(
                    new FieldId("EN_FJARDEDEL"),
                    "25 procent",
                    CodeSystemKvFkmu0008.EN_FJARDEDEL
                ),
                new ElementConfigurationCode(
                    new FieldId("HALVA"),
                    "50 procent",
                    CodeSystemKvFkmu0008.HALVA
                ),
                new ElementConfigurationCode(
                    new FieldId("TRE_FJARDEDELAR"),
                    "75 procent",
                    CodeSystemKvFkmu0008.TRE_FJARDEDELAR
                ),
                new ElementConfigurationCode(
                    new FieldId("HELA"),
                    "100 procent",
                    CodeSystemKvFkmu0008.HELA
                )
            )
        )
        .build();

    final var element = QuestionPeriod.questionPeriod();

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
                    "$EN_ATTONDEL || $EN_FJARDEDEL || $HALVA || $TRE_FJARDEDELAR || $HELA"
                )
            )
            .build()
    );

    final var element = QuestionPeriod.questionPeriod();

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shallIncludeValidations() {
    final var expectedValidations = List.of(
        ElementValidationDateRangeList.builder()
            .min(Period.ofDays(-90))
            .mandatory(true)
            .build()
    );

    final var element = QuestionPeriod.questionPeriod();

    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shallIncludeWhenRenewingFalse() {
    final var element = QuestionPeriod.questionPeriod();

    assertEquals(Boolean.FALSE, element.includeWhenRenewing());
  }

  @Test
  void shallIncludePdfConfiguration() {
    final var expected = PdfConfigurationDateRangeList.builder()
        .pdfFieldId(new PdfFieldId("form1[0].#subform[0]"))
        .build();

    final var element = QuestionPeriod.questionPeriod();

    assertEquals(expected, element.pdfConfiguration());
  }
}