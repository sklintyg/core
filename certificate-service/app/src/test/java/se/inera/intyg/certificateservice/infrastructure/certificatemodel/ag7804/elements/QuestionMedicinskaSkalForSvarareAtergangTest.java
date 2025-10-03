package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleLimit;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleLimit;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;

class QuestionMedicinskaSkalForSvarareAtergangTest {

  private static final ElementId ELEMENT_ID = new ElementId("33.2");

  @Test
  void shouldIncludeId() {
    final var element = QuestionMedicinskaSkalForSvarareAtergang.questionMedicinskaSkalForSvarareAtergang();
    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shouldIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationTextArea.builder()
        .name(
            "Beskriv de medicinska skälen till att möjligheterna till återgång i arbete försämras")
        .id(new FieldId("33.2"))
        .build();

    final var element = QuestionMedicinskaSkalForSvarareAtergang.questionMedicinskaSkalForSvarareAtergang();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shouldIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .type(ElementRuleType.MANDATORY)
            .id(ELEMENT_ID)
            .expression(new RuleExpression("$33.2"))
            .build(),
        ElementRuleLimit.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.TEXT_LIMIT)
            .limit(new RuleLimit((short) 4000))
            .build(),
        ElementRuleExpression.builder()
            .id(new ElementId("33"))
            .type(ElementRuleType.SHOW)
            .expression(new RuleExpression("$33.1"))
            .build()
    );

    final var element = QuestionMedicinskaSkalForSvarareAtergang.questionMedicinskaSkalForSvarareAtergang();

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

    final var element = QuestionMedicinskaSkalForSvarareAtergang.questionMedicinskaSkalForSvarareAtergang();

    assertEquals(expectedValidations, element.validations());
  }

  @Nested
  class ShouldValidate {

    @Test
    void shouldReturnTrueIfBooleanIsTrue() {
      final var element = QuestionMedicinskaSkalForSvarareAtergang.questionMedicinskaSkalForSvarareAtergang();
      final var shouldValidate = element.elementSpecification(new ElementId("33.2"))
          .shouldValidate();
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("33"))
              .value(ElementValueBoolean.builder().value(true).build())
              .build()
      );
      assertTrue(shouldValidate.test(elementData));
    }

    @Test
    void shouldReturnFalseIfBooleanIsFalse() {
      final var element = QuestionMedicinskaSkalForSvarareAtergang.questionMedicinskaSkalForSvarareAtergang();
      final var shouldValidate = element.elementSpecification(new ElementId("33.2"))
          .shouldValidate();
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("33"))
              .value(ElementValueBoolean.builder().value(false).build())
              .build()
      );
      assertFalse(shouldValidate.test(elementData));
    }

    @Test
    void shouldReturnFalseIfElementMissing() {
      final var element = QuestionMedicinskaSkalForSvarareAtergang.questionMedicinskaSkalForSvarareAtergang();
      final var shouldValidate = element.elementSpecification(new ElementId("33.2"))
          .shouldValidate();
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("not33"))
              .value(ElementValueBoolean.builder().value(true).build())
              .build()
      );
      assertFalse(shouldValidate.test(elementData));
    }
  }

}