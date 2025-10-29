package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvAnatomiskLokalisationHorapparat.HOGER;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvAnatomiskLokalisationHorapparat.VANSTER;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionHorselV2.QUESTION_HORSEL_V2_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionHorselhjalpmedelV2.QUESTION_HORSELHJALPMEDEL_V2_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionHorselhjalpmedelV2.QUESTION_HORSELHJALPMEDEL_V2_ID;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementLayout;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationCodeList;

class QuestionHorselhjalpmedelPositionV2Test {

  private static final ElementId ELEMENT_ID = new ElementId("9.3");

  @Test
  void shouldIncludeId() {
    final var element = QuestionHorselhjalpmedelPositionV2.questionHorselhjalpmedelPositionV2();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shouldIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCheckboxMultipleCode.builder()
        .name("Om personen behöver använda hörapparat, ange på vilket öra")
        .id(new FieldId("9.3"))
        .elementLayout(ElementLayout.INLINE)
        .list(
            List.of(
                new ElementConfigurationCode(
                    new FieldId(HOGER.code()),
                    HOGER.displayName(),
                    HOGER
                ),
                new ElementConfigurationCode(
                    new FieldId(VANSTER.code()),
                    VANSTER.displayName(),
                    VANSTER
                )
            )
        )
        .build();

    final var element = QuestionHorselhjalpmedelPositionV2.questionHorselhjalpmedelPositionV2();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shouldIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.MANDATORY)
            .expression(new RuleExpression(
                String.format("exists($%s) || exists($%s)", HOGER.code(), VANSTER.code())))
            .build(),
        ElementRuleExpression.builder()
            .id(QUESTION_HORSELHJALPMEDEL_V2_ID)
            .type(ElementRuleType.SHOW)
            .expression(new RuleExpression("$" + QUESTION_HORSELHJALPMEDEL_V2_FIELD_ID.value()))
            .build()
    );

    final var element = QuestionHorselhjalpmedelPositionV2.questionHorselhjalpmedelPositionV2();

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shouldIncludeValidations() {
    final var expectedValidations = List.of(
        ElementValidationCodeList.builder()
            .mandatory(true)
            .build()
    );

    final var element = QuestionHorselhjalpmedelPositionV2.questionHorselhjalpmedelPositionV2();

    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shouldIncludeMapping() {
    final var element = QuestionHorselhjalpmedelPositionV2.questionHorselhjalpmedelPositionV2();

    assertEquals(new ElementMapping(QUESTION_HORSEL_V2_ID, null), element.mapping());
  }

  @Nested
  class ShouldValidate {

    @Test
    void shouldReturnTrueIfBooleanIsTrue() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("9.2"))
              .value(
                  ElementValueBoolean.builder()
                      .value(true)
                      .build()
              )
              .build()
      );

      final var element = QuestionHorselhjalpmedelPositionV2.questionHorselhjalpmedelPositionV2();

      final var shouldValidate = element.shouldValidate();

      assertTrue(shouldValidate.test(elementData));
    }

    @Test
    void shouldReturnFalseIfElementMissing() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("9.1"))
              .value(
                  ElementValueBoolean.builder()
                      .value(true)
                      .build()
              )
              .build()
      );

      final var element = QuestionHorselhjalpmedelPositionV2.questionHorselhjalpmedelPositionV2();

      final var shouldValidate = element.shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }

    @Test
    void shouldReturnFalseIfElementFalse() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("9.2"))
              .value(
                  ElementValueBoolean.builder()
                      .value(false)
                      .build()
              )
              .build()
      );

      final var element = QuestionHorselhjalpmedelPositionV2.questionHorselhjalpmedelPositionV2();

      final var shouldValidate = element.shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }
  }
}

