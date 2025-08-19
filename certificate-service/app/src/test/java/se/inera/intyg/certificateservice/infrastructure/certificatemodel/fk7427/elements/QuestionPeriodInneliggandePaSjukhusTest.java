package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDateRange;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationDateRange;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDateRange;

class QuestionPeriodInneliggandePaSjukhusTest {

  private static final ElementId ELEMENT_ID = new ElementId("62.2");

  @Test
  void shouldIncludeId() {
    final var element = QuestionPeriodInneliggandePaSjukhus.questionPeriodInneliggandePaSjukhus();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shouldIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationDateRange.builder()
        .name("Ange period")
        .labelFrom("Fr.o.m")
        .labelTo("T.o.m")
        .id(new FieldId("62.2"))
        .build();

    final var element = QuestionPeriodInneliggandePaSjukhus.questionPeriodInneliggandePaSjukhus();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shouldIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(new ElementId("62.2"))
            .type(ElementRuleType.MANDATORY)
            .expression(new RuleExpression("$62.2"))
            .build(),
        ElementRuleExpression.builder()
            .id(new ElementId("62.1"))
            .type(ElementRuleType.SHOW)
            .expression(new RuleExpression("$62.1"))
            .build()
    );

    final var element = QuestionPeriodInneliggandePaSjukhus.questionPeriodInneliggandePaSjukhus();

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shouldIncludeValidation() {
    final var expectedValidation = ElementValidationDateRange.builder()
        .mandatory(true)
        .build();

    final var element = QuestionPeriodInneliggandePaSjukhus.questionPeriodInneliggandePaSjukhus();

    assertEquals(List.of(expectedValidation), element.validations());
  }

  @Test
  void shouldIncludePdfConfiguration() {
    final var expectedPdfConfiguration = PdfConfigurationDateRange.builder()
        .from(new PdfFieldId("form1[0].#subform[2].flt_datumFranMed2[0]"))
        .to(new PdfFieldId("form1[0].#subform[2].flt_datumTillMed2[0]"))
        .build();

    final var element = QuestionPeriodInneliggandePaSjukhus.questionPeriodInneliggandePaSjukhus();

    assertEquals(expectedPdfConfiguration, element.pdfConfiguration());
  }

  @Test
  void shouldIncludeElementMapping() {
    final var element = QuestionPeriodInneliggandePaSjukhus.questionPeriodInneliggandePaSjukhus();

    assertEquals(
        new ElementMapping(new ElementId("62"), null),
        element.mapping()
    );
  }

  @Nested
  class ShouldValidate {

    @Test
    void shouldReturnTrueIfBooleanIsTrue() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("62.1"))
              .value(
                  ElementValueBoolean.builder()
                      .value(true)
                      .build()
              )
              .build()
      );

      final var element = QuestionPeriodInneliggandePaSjukhus.questionPeriodInneliggandePaSjukhus();

      final var shouldValidate = element.shouldValidate();

      assertTrue(shouldValidate.test(elementData));
    }

    @Test
    void shouldReturnFalseIfElementMissing() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("62.3"))
              .value(
                  ElementValueBoolean.builder()
                      .value(true)
                      .build()
              )
              .build()
      );

      final var element = QuestionPeriodInneliggandePaSjukhus.questionPeriodInneliggandePaSjukhus();

      final var shouldValidate = element.shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }

    @Test
    void shouldReturnFalseIfBooleanIsFalse() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("62.1"))
              .value(
                  ElementValueBoolean.builder()
                      .value(false)
                      .build()
              )
              .build()
      );

      final var element = QuestionPeriodInneliggandePaSjukhus.questionPeriodInneliggandePaSjukhus();

      final var shouldValidate = element.shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }
  }

  @Test
  void shouldNotIncludeWhenRenewing() {
    final var element = QuestionPeriodInneliggandePaSjukhus.questionPeriodInneliggandePaSjukhus();
    assertFalse(element.includeWhenRenewing());
  }
}
