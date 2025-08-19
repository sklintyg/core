package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements;

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

class QuestionPeriodVardasBarnetInskrivetMedHemsjukvardTest {

  private static final ElementId ELEMENT_ID = new ElementId("62.4");

  @Test
  void shouldIncludeId() {
    final var element = QuestionPeriodVardasBarnetInskrivetMedHemsjukvard.questionPeriodVardasBarnetInskrivetMedHemsjukvard();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shouldIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationDateRange.builder()
        .name("Ange period")
        .labelFrom("Fr.o.m")
        .labelTo("T.o.m")
        .id(new FieldId("62.4"))
        .build();

    final var element = QuestionPeriodVardasBarnetInskrivetMedHemsjukvard.questionPeriodVardasBarnetInskrivetMedHemsjukvard();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shouldIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(new ElementId("62.4"))
            .type(ElementRuleType.MANDATORY)
            .expression(new RuleExpression("$62.4"))
            .build(),
        ElementRuleExpression.builder()
            .id(new ElementId("62.3"))
            .type(ElementRuleType.SHOW)
            .expression(new RuleExpression("$62.3"))
            .build()
    );

    final var element = QuestionPeriodVardasBarnetInskrivetMedHemsjukvard.questionPeriodVardasBarnetInskrivetMedHemsjukvard();

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shouldIncludeValidation() {
    final var expectedValidation = ElementValidationDateRange.builder()
        .mandatory(true)
        .build();

    final var element = QuestionPeriodVardasBarnetInskrivetMedHemsjukvard.questionPeriodVardasBarnetInskrivetMedHemsjukvard();

    assertEquals(List.of(expectedValidation), element.validations());
  }

  @Test
  void shouldIncludeElementMapping() {
    final var element = QuestionPeriodVardasBarnetInskrivetMedHemsjukvard.questionPeriodVardasBarnetInskrivetMedHemsjukvard();

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
              .id(new ElementId("62.3"))
              .value(
                  ElementValueBoolean.builder()
                      .value(true)
                      .build()
              )
              .build()
      );

      final var element = QuestionPeriodVardasBarnetInskrivetMedHemsjukvard.questionPeriodVardasBarnetInskrivetMedHemsjukvard();

      final var shouldValidate = element.shouldValidate();

      assertTrue(shouldValidate.test(elementData));
    }

    @Test
    void shouldReturnFalseIfElementMissing() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("62"))
              .value(
                  ElementValueBoolean.builder()
                      .value(true)
                      .build()
              )
              .build()
      );

      final var element = QuestionPeriodVardasBarnetInskrivetMedHemsjukvard.questionPeriodVardasBarnetInskrivetMedHemsjukvard();

      final var shouldValidate = element.shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }

    @Test
    void shouldReturnFalseIfBooleanIsFalse() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("62.3"))
              .value(
                  ElementValueBoolean.builder()
                      .value(false)
                      .build()
              )
              .build()
      );

      final var element = QuestionPeriodVardasBarnetInskrivetMedHemsjukvard.questionPeriodVardasBarnetInskrivetMedHemsjukvard();

      final var shouldValidate = element.shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }

    @Test
    void shouldIncludePdfConfiguration() {
      final var expectedPdfConfiguration = PdfConfigurationDateRange.builder()
          .from(new PdfFieldId("form1[0].#subform[3].flt_datFranMed2[0]"))
          .to(new PdfFieldId("form1[0].#subform[3].flt_datTillMed2[0]"))
          .build();

      final var element = QuestionPeriodVardasBarnetInskrivetMedHemsjukvard.questionPeriodVardasBarnetInskrivetMedHemsjukvard();

      assertEquals(expectedPdfConfiguration, element.pdfConfiguration());
    }
  }

  @Test
  void shouldHaveIncludeWhenRenewingFalse() {
    final var element = QuestionPeriodVardasBarnetInskrivetMedHemsjukvard.questionPeriodVardasBarnetInskrivetMedHemsjukvard();
    assertFalse(element.includeWhenRenewing());
  }
}
