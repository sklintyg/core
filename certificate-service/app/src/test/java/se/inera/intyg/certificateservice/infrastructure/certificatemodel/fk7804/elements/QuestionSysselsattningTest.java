package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0002.ARBETSSOKANDE;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0002.FORALDRALEDIG;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0002.NUVARANDE_ARBETE;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0002.STUDIER;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionSmittbararpenning.QUESTION_SMITTBARARPENNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionSysselsattning.QUESTION_SYSSELSATTNING_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionSysselsattning.QUESTION_SYSSELSATTNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionSysselsattning.questionSysselsattning;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementLayout;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationCodeList;

class QuestionSysselsattningTest {

  @Test
  void shouldHaveCorrectId() {
    final var element = QuestionSysselsattning.questionSysselsattning();
    assertEquals(QUESTION_SYSSELSATTNING_ID, element.id());
  }

  @Test
  void shouldHaveCorrectConfiguration() {
    final var expectedConfiguration = ElementConfigurationCheckboxMultipleCode.builder()
        .id(QUESTION_SYSSELSATTNING_FIELD_ID)
        .name("I relation till vilken sysselsättning bedömer du arbetsförmågan?")
        .elementLayout(ElementLayout.ROWS)
        .list(
            List.of(
                new ElementConfigurationCode(new FieldId(NUVARANDE_ARBETE.code()),
                    NUVARANDE_ARBETE.displayName(), NUVARANDE_ARBETE),
                new ElementConfigurationCode(new FieldId(ARBETSSOKANDE.code()),
                    ARBETSSOKANDE.displayName(), ARBETSSOKANDE),
                new ElementConfigurationCode(new FieldId(FORALDRALEDIG.code()),
                    FORALDRALEDIG.displayName(), FORALDRALEDIG),
                new ElementConfigurationCode(new FieldId(STUDIER.code()), STUDIER.displayName(),
                    STUDIER)
            )
        )
        .description(
            "Om du kryssar i flera val är det viktigt att du tydliggör under \"Övriga upplysningar\" om sjukskrivningens omfattning eller period skiljer sig åt mellan olika sysselsättningar.")
        .build();

    final var element = QuestionSysselsattning.questionSysselsattning();
    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shouldIncludeRules() {
    final var element = QuestionSysselsattning.questionSysselsattning();
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(QUESTION_SYSSELSATTNING_ID)
            .type(ElementRuleType.MANDATORY)
            .expression(
                new RuleExpression(
                    "exists($ARBETSSOKANDE) || exists($FORALDRALEDIG) || exists($NUVARANDE_ARBETE) || exists($STUDIER)"
                )
            )
            .build(),
        ElementRuleExpression.builder()
            .id(QUESTION_SMITTBARARPENNING_ID)
            .type(ElementRuleType.HIDE)
            .expression(
                new RuleExpression(
                    "$27.1"
                )
            )
            .build()
    );
    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shouldIncludeValidation() {
    final var element = QuestionSysselsattning.questionSysselsattning();
    final var expectedValidations = List.of(
        ElementValidationCodeList.builder()
            .mandatory(true)
            .build()
    );
    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shouldHaveCorrectPdfConfiguration() {
    final var expected = PdfConfigurationCode.builder()
        .codes(Map.of(
            new FieldId(NUVARANDE_ARBETE.code()),
            new PdfFieldId("form1[0].#subform[0].ksr_NuvarandeArbete[0]"),
            new FieldId(ARBETSSOKANDE.code()),
            new PdfFieldId("form1[0].#subform[0].ksr_Arbetssokande[0]"),
            new FieldId(FORALDRALEDIG.code()),
            new PdfFieldId("form1[0].#subform[0].ksr_Foraldraledighet[0]"),
            new FieldId(STUDIER.code()), new PdfFieldId("form1[0].#subform[0].ksr_Studier[0]")
        ))
        .build();
    assertEquals(expected, questionSysselsattning().pdfConfiguration());
  }

  @Nested
  class ShouldValidate {

    @Test
    void shallReturnTrueIfBooleanIsFalse() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("27"))
              .value(
                  ElementValueBoolean.builder()
                      .value(false)
                      .build()
              )
              .build()
      );

      final var element = QuestionSysselsattning.questionSysselsattning();

      final var shouldValidate = element.elementSpecification(QUESTION_SYSSELSATTNING_ID)
          .shouldValidate();

      assertTrue(shouldValidate.test(elementData));
    }

    @Test
    void shallReturnTrueIfElementMissing() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("8.1"))
              .value(
                  ElementValueBoolean.builder()
                      .value(true)
                      .build()
              )
              .build()
      );

      final var element = QuestionSysselsattning.questionSysselsattning();

      final var shouldValidate = element.elementSpecification(QUESTION_SYSSELSATTNING_ID)
          .shouldValidate();

      assertTrue(shouldValidate.test(elementData));
    }

    @Test
    void shallReturnFalseIfElementTrue() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("27"))
              .value(
                  ElementValueBoolean.builder()
                      .value(true)
                      .build()
              )
              .build()
      );

      final var element = QuestionSysselsattning.questionSysselsattning();

      final var shouldValidate = element.elementSpecification(QUESTION_SYSSELSATTNING_ID)
          .shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }
  }
}
