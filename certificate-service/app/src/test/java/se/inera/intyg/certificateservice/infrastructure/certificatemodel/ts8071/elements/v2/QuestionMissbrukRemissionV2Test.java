package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementLayout;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationCode;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvTs001;

class QuestionMissbrukRemissionV2Test {

  private static final ElementId ELEMENT_ID = new ElementId("18.10");

  @Test
  void shouldIncludeId() {
    final var element = QuestionMissbrukRemissionV2.questionMissbrukRemissionV2();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shouldIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationRadioMultipleCode.builder()
        .id(new FieldId("18.10"))
        .name("Om diagnos beroende, är beroendet i fullständig långvarig remission?")
        .description(
            "Här avses exempelvis beroende eller skadligt mönster av bruk enligt ICD-11, skadligt bruk enligt ICD-10, missbruk enligt DSM-IV eller substansbrukssyndrom enligt DSM-5.")
        .elementLayout(ElementLayout.ROWS)
        .list(
            List.of(
                CodeFactory.elementConfigurationCode(CodeSystemKvTs001.JA),
                CodeFactory.elementConfigurationCode(CodeSystemKvTs001.NEJ),
                CodeFactory.elementConfigurationCode(CodeSystemKvTs001.VET_INTE)
            )
        )
        .build();

    final var element = QuestionMissbrukRemissionV2.questionMissbrukRemissionV2();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shouldIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.MANDATORY)
            .expression(
                new RuleExpression(
                    "exists($ja) || exists($nej) || exists($vetinte)"
                )
            )
            .build(),
        ElementRuleExpression.builder()
            .id(new ElementId("18"))
            .type(ElementRuleType.SHOW)
            .expression(
                new RuleExpression(
                    "$18.1"
                )
            )
            .build()
    );

    final var element = QuestionMissbrukRemissionV2.questionMissbrukRemissionV2();

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shouldIncludeValidation() {
    final var expectedValidations = List.of(
        ElementValidationCode.builder()
            .mandatory(true)
            .build()
    );

    final var element = QuestionMissbrukRemissionV2.questionMissbrukRemissionV2();

    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shouldIncludeMapping() {
    final var element = QuestionMissbrukRemissionV2.questionMissbrukRemissionV2();

    assertEquals(new ElementMapping(new ElementId("18"), null), element.mapping());
  }

  @Nested
  class ShouldValidate {

    @Test
    void shouldReturnTrueIfBooleanIsTrue() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("18"))
              .value(
                  ElementValueBoolean.builder()
                      .value(true)
                      .build()
              )
              .build()
      );

      final var element = QuestionMissbrukRemissionV2.questionMissbrukRemissionV2();

      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();

      assertTrue(shouldValidate.test(elementData));
    }

    @Test
    void shouldReturnFalseIfElementMissing() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("17"))
              .value(
                  ElementValueBoolean.builder()
                      .value(true)
                      .build()
              )
              .build()
      );

      final var element = QuestionMissbrukRemissionV2.questionMissbrukRemissionV2();

      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }

    @Test
    void shouldReturnFalseIfElementFalse() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("18"))
              .value(
                  ElementValueBoolean.builder()
                      .value(false)
                      .build()
              )
              .build()
      );

      final var element = QuestionMissbrukRemissionV2.questionMissbrukRemissionV2();

      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }
  }
}

