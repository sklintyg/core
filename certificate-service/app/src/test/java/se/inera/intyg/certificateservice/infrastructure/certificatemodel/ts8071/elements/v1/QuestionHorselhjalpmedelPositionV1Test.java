package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvAnatomiskLokalisationHorapparat.BADA_ORONEN;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvAnatomiskLokalisationHorapparat.HOGER;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvAnatomiskLokalisationHorapparat.VANSTER;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementLayout;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationCode;

class QuestionHorselhjalpmedelPositionV1Test {

  private static final ElementId ELEMENT_ID = new ElementId("9.3");

  @Test
  void shallIncludeId() {
    final var element = QuestionHorselhjalpmedelPositionV1.questionHorselhjalpmedelPositionV1();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationRadioMultipleCode.builder()
        .id(new FieldId("9.3"))
        .name(
            "Om personen behöver använda hörapparat, ange på vilket öra eller om hörapparat används på båda öronen")
        .elementLayout(ElementLayout.ROWS)
        .list(
            List.of(
                new ElementConfigurationCode(
                    new FieldId(VANSTER.code()),
                    VANSTER.displayName(),
                    VANSTER
                ),
                new ElementConfigurationCode(
                    new FieldId(HOGER.code()),
                    HOGER.displayName(),
                    HOGER
                ),
                new ElementConfigurationCode(
                    new FieldId(BADA_ORONEN.code()),
                    BADA_ORONEN.displayName(),
                    BADA_ORONEN
                )
            )
        )
        .build();

    final var element = QuestionHorselhjalpmedelPositionV1.questionHorselhjalpmedelPositionV1();

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
                    "exists($89644007) || exists($25577004) || exists($34338003)"
                )
            )
            .build(),
        ElementRuleExpression.builder()
            .id(new ElementId("9.2"))
            .type(ElementRuleType.SHOW)
            .expression(new RuleExpression("$9.2"))
            .build()
    );

    final var element = QuestionHorselhjalpmedelPositionV1.questionHorselhjalpmedelPositionV1();

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shallIncludeValidations() {
    final var expectedValidations = List.of(
        ElementValidationCode.builder()
            .mandatory(true)
            .build()
    );

    final var element = QuestionHorselhjalpmedelPositionV1.questionHorselhjalpmedelPositionV1();

    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shallIncludeMapping() {
    final var element = QuestionHorselhjalpmedelPositionV1.questionHorselhjalpmedelPositionV1();

    assertEquals(new ElementMapping(new ElementId("9"), null), element.mapping());
  }

  @Nested
  class ShouldValidate {

    @Test
    void shallReturnTrueIfBooleanIsTrue() {
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

      final var element = QuestionHorselhjalpmedelPositionV1.questionHorselhjalpmedelPositionV1();

      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();

      assertTrue(shouldValidate.test(elementData));
    }

    @Test
    void shallReturnFalseIfElementMissing() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("7.1"))
              .value(
                  ElementValueBoolean.builder()
                      .value(true)
                      .build()
              )
              .build()
      );

      final var element = QuestionHorselhjalpmedelPositionV1.questionHorselhjalpmedelPositionV1();

      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }

    @Test
    void shallReturnFalseIfElementFalse() {
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

      final var element = QuestionHorselhjalpmedelPositionV1.questionHorselhjalpmedelPositionV1();

      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }
  }
}