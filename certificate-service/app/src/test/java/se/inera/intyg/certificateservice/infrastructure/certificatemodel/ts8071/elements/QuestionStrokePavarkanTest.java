package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvTs001.NO;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvTs001.NO_KNOWLEDGE;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvTs001.YES;

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

class QuestionStrokePavarkanTest {

  private static final ElementId ELEMENT_ID = new ElementId("11.10");

  @Test
  void shallIncludeId() {
    final var element = QuestionStrokePavarkan.questionStrokePavarkan();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationRadioMultipleCode.builder()
        .id(new FieldId("11.10"))
        .name(
            "Om stroke förekommit, har den inträffat i/påverkat syncentrum (occipitalloben eller synnerven)?")
        .elementLayout(ElementLayout.ROWS)
        .list(
            List.of(
                new ElementConfigurationCode(
                    new FieldId(YES.code()),
                    YES.displayName(),
                    YES
                ),
                new ElementConfigurationCode(
                    new FieldId(NO.code()),
                    NO.displayName(),
                    NO
                ),
                new ElementConfigurationCode(
                    new FieldId(NO_KNOWLEDGE.code()),
                    NO_KNOWLEDGE.displayName(),
                    NO_KNOWLEDGE
                )
            )
        )
        .build();

    final var element = QuestionStrokePavarkan.questionStrokePavarkan();

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
                    "exists($ja) || exists($nej) || exists($vetej)"
                )
            )
            .build(),
        ElementRuleExpression.builder()
            .id(new ElementId("11.9"))
            .type(ElementRuleType.SHOW)
            .expression(new RuleExpression("$11.9"))
            .build()
    );

    final var element = QuestionStrokePavarkan.questionStrokePavarkan();

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shallIncludeValidations() {
    final var expectedValidations = List.of(
        ElementValidationCode.builder()
            .mandatory(true)
            .build()
    );

    final var element = QuestionStrokePavarkan.questionStrokePavarkan();

    assertEquals(expectedValidations, element.validations());
  }


  @Test
  void shallIncludeMapping() {
    final var element = QuestionStrokePavarkan.questionStrokePavarkan();

    assertEquals(new ElementMapping(new ElementId("11"), null), element.mapping());
  }

  @Nested
  class ShouldValidate {

    @Test
    void shallReturnTrueIfBooleanIsTrue() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("11.9"))
              .value(
                  ElementValueBoolean.builder()
                      .value(true)
                      .build()
              )
              .build()
      );

      final var element = QuestionStrokePavarkan.questionStrokePavarkan();

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

      final var element = QuestionStrokePavarkan.questionStrokePavarkan();

      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }

    @Test
    void shallReturnFalseIfElementFalse() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("11.9"))
              .value(
                  ElementValueBoolean.builder()
                      .value(false)
                      .build()
              )
              .build()
      );

      final var element = QuestionStrokePavarkan.questionStrokePavarkan();

      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }
  }
}