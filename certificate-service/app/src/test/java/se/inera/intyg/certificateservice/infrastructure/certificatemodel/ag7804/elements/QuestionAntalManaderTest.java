package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionAntalManader.QUESTION_ANTAL_MANADER_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionAntalManader.QUESTION_ANTAL_MANADER_ID;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationInteger;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationInteger;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0006;

class QuestionAntalManaderTest {

  @Test
  void shouldIncludeId() {
    final var element = QuestionAntalManader.questionAntalManader();
    assertEquals(QUESTION_ANTAL_MANADER_ID, element.id());
  }

  @Test
  void shouldIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationInteger.builder()
        .id(QUESTION_ANTAL_MANADER_FIELD_ID)
        .name("Ange antal månader")
        .min(1)
        .max(99)
        .build();

    final var element = QuestionAntalManader.questionAntalManader();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shouldIncludeValidation() {
    final var element = QuestionAntalManader.questionAntalManader();
    final var expectedValidations = List.of(
        ElementValidationInteger.builder()
            .mandatory(true)
            .min(1)
            .max(99)
            .build()
    );
    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shouldIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .type(ElementRuleType.MANDATORY)
            .id(QUESTION_ANTAL_MANADER_ID)
            .expression(new RuleExpression("$" + QUESTION_ANTAL_MANADER_ID.id()))
            .build(),
        ElementRuleExpression.builder()
            .type(ElementRuleType.SHOW)
            .id(new ElementId("39"))
            .expression(new RuleExpression("$" + CodeSystemKvFkmu0006.ATER_X_ANTAL_MANADER.code()))
            .build()

    );

    final var element = QuestionAntalManader.questionAntalManader();

    assertEquals(expectedRules, element.rules());
  }

  @Nested
  class ShouldValidate {

    @Test
    void shouldReturnTrueIfCodeIsAterXAntalManader() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("39"))
              .value(
                  ElementValueCode.builder()
                      .codeId(new FieldId(CodeSystemKvFkmu0006.ATER_X_ANTAL_MANADER.code()))
                      .build()
              )
              .build()
      );
      final var element = QuestionAntalManader.questionAntalManader();
      final var shouldValidate = element.elementSpecification(QUESTION_ANTAL_MANADER_ID)
          .shouldValidate();
      assertTrue(shouldValidate.test(elementData));
    }

    @Test
    void shouldReturnFalseIfElementMissing() {
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
      final var element = QuestionAntalManader.questionAntalManader();
      final var shouldValidate = element.elementSpecification(QUESTION_ANTAL_MANADER_ID)
          .shouldValidate();
      assertFalse(shouldValidate.test(elementData));
    }

    @Test
    void shouldReturnFalseIfElementCodeIsNotAterXAntalManader() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("39"))
              .value(
                  ElementValueCode.builder()
                      .codeId(new FieldId("TEST"))
                      .build()
              )
              .build()
      );
      final var element = QuestionAntalManader.questionAntalManader();
      final var shouldValidate = element.elementSpecification(QUESTION_ANTAL_MANADER_ID)
          .shouldValidate();
      assertFalse(shouldValidate.test(elementData));
    }
  }

  @Test
  void shouldNotIncludeWhenRenewing() {
    final var element = QuestionAntalManader.questionAntalManader();
    assertFalse(element.includeWhenRenewing());
  }

}