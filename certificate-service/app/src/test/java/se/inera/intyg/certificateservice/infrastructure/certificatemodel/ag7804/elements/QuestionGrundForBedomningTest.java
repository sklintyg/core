package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleLimit;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleLimit;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0006;

class QuestionGrundForBedomningTest {

  private static final ElementId ELEMENT_ID = new ElementId("39.2");
  private static final FieldId FIELD_ID = new FieldId("39.2");

  @Test
  void shouldIncludeId() {
    final var element = QuestionGrundForBedomning.questionGrundForBedomning();
    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shouldIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationTextArea.builder()
        .id(FIELD_ID)
        .name("Beskriv vad som ligger till grund för bedömningen")
        .build();
    final var element = QuestionGrundForBedomning.questionGrundForBedomning();
    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shouldIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .type(ElementRuleType.MANDATORY)
            .id(ELEMENT_ID)
            .expression(new RuleExpression("$" + ELEMENT_ID.id()))
            .build(),
        ElementRuleLimit.builder()
            .type(ElementRuleType.TEXT_LIMIT)
            .id(ELEMENT_ID)
            .limit(new RuleLimit((short) 4000))
            .build(),
        ElementRuleExpression.builder()
            .type(ElementRuleType.SHOW)
            .id(new ElementId("39"))
            .expression(new RuleExpression(
                "$" + CodeSystemKvFkmu0006.PROGNOS_OKLAR.code()))
            .build()
    );

    final var element = QuestionGrundForBedomning.questionGrundForBedomning();

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shouldIncludeValidations() {
    final var expectedValidations = List.of(
        ElementValidationText.builder()
            .mandatory(true)
            .limit(4000)
            .build()
    );
    final var element = QuestionGrundForBedomning.questionGrundForBedomning();
    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shouldNotIncludeWhenRenewing() {
    final var element = QuestionGrundForBedomning.questionGrundForBedomning();
    assertFalse(element.includeWhenRenewing());
  }

  @Nested
  class ShouldValidate {

    @Test
    void shouldReturnTrueIfPrognosOklart() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("39"))
              .value(
                  ElementValueCode.builder()
                      .codeId(new FieldId(CodeSystemKvFkmu0006.PROGNOS_OKLAR.code()))
                      .build()
              )
              .build()
      );
      final var element = QuestionGrundForBedomning.questionGrundForBedomning();
      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();
      assertTrue(shouldValidate.test(elementData));
    }

    @Test
    void shouldReturnFalseIfNotPrognosOklart() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("39"))
              .value(
                  ElementValueCode.builder()
                      .codeId(new FieldId("OTHER_CODE"))
                      .build()
              )
              .build()
      );
      final var element = QuestionGrundForBedomning.questionGrundForBedomning();
      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();
      assertFalse(shouldValidate.test(elementData));
    }
  }

}