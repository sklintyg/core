package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionSysselsattning.QUESTION_SYSSELSATTNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionYrkeOchArbetsuppgifter.QUESTION_YRKE_ARBETSUPPGIFTER_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionYrkeOchArbetsuppgifter.QUESTION_YRKE_ARBETSUPPGIFTER_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0002.NUVARANDE_ARBETE;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0002.STUDIER;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCodeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleLimit;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleLimit;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;

class QuestionYrkeOchArbetsuppgifterTest {

  @Test
  void shouldIncludeId() {
    final var element = se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionYrkeOchArbetsuppgifter.questionYrkeOchArbetsuppgifter();
    assertEquals(QUESTION_YRKE_ARBETSUPPGIFTER_ID, element.id());
  }

  @Test
  void shouldIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationTextArea.builder()
        .id(QUESTION_YRKE_ARBETSUPPGIFTER_FIELD_ID)
        .name("Ange yrke och arbetsuppgifter")
        .build();

    final var element = se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionYrkeOchArbetsuppgifter.questionYrkeOchArbetsuppgifter();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shouldIncludeValidation() {
    final var element = se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionYrkeOchArbetsuppgifter.questionYrkeOchArbetsuppgifter();
    final var expectedValidations = List.of(
        ElementValidationText.builder()
            .mandatory(true)
            .limit(4000)
            .build()
    );
    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shouldIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .type(ElementRuleType.SHOW)
            .expression(new RuleExpression("$NUVARANDE_ARBETE"))
            .id(QUESTION_SYSSELSATTNING_ID)
            .build(),
        ElementRuleExpression.builder()
            .type(ElementRuleType.MANDATORY)
            .id(QUESTION_YRKE_ARBETSUPPGIFTER_ID)
            .expression(new RuleExpression("$" + QUESTION_YRKE_ARBETSUPPGIFTER_FIELD_ID.value()))
            .build(),
        ElementRuleLimit.builder()
            .type(ElementRuleType.TEXT_LIMIT)
            .id(QUESTION_YRKE_ARBETSUPPGIFTER_ID)
            .limit(new RuleLimit((short) 4000))
            .build()
    );

    final var element = se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionYrkeOchArbetsuppgifter.questionYrkeOchArbetsuppgifter();

    assertEquals(expectedRules, element.rules());
  }

  @Nested
  class ShouldValidate {

    @Test
    void shallReturnTrueIfCodeIsNuvarandeArbete() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("28"))
              .value(
                  ElementValueCodeList.builder()
                      .list(
                          List.of(
                              ElementValueCode.builder()
                                  .codeId(new FieldId(NUVARANDE_ARBETE.code()))
                                  .code(NUVARANDE_ARBETE.code())
                                  .build()
                          )
                      )
                      .build()
              )
              .build()
      );

      final var element = se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionYrkeOchArbetsuppgifter.questionYrkeOchArbetsuppgifter();

      final var shouldValidate = element.elementSpecification(new ElementId("29")).shouldValidate();

      assertTrue(shouldValidate.test(elementData));
    }

    @Test
    void shallReturnFalseIfElementCodeIsNotNuvarandeArbete() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("28"))
              .value(
                  ElementValueCodeList.builder()
                      .list(
                          List.of(
                              ElementValueCode.builder()
                                  .codeId(new FieldId(STUDIER.code()))
                                  .code(STUDIER.code())
                                  .build()
                          )
                      )
                      .build()
              )
              .build()
      );

      final var element = QuestionYrkeOchArbetsuppgifter.questionYrkeOchArbetsuppgifter();

      final var shouldValidate = element.elementSpecification(new ElementId("29")).shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }
  }
}