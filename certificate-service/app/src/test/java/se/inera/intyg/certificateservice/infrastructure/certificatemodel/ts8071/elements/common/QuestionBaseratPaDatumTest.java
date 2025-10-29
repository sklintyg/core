package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Period;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDate;

class QuestionBaseratPaDatumTest {

  private static final ElementId ELEMENT_ID = new ElementId("2.2");

  @Test
  void shallIncludeId() {
    final var element = QuestionBaseratPaDatum.questionBaseratPaDatum();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationDate.builder()
        .name("Datum för undersökning/kontakt")
        .id(new FieldId("2.2"))
        .max(Period.ofDays(0))
        .build();

    final var element = QuestionBaseratPaDatum.questionBaseratPaDatum();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shallIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.MANDATORY)
            .expression(new RuleExpression("$2.2"))
            .build(),
        ElementRuleExpression.builder()
            .id(new ElementId("2"))
            .type(ElementRuleType.SHOW)
            .expression(new RuleExpression("exists(distkont) || exists(undersokn)"))
            .build()
    );

    final var element = QuestionBaseratPaDatum.questionBaseratPaDatum();

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shallIncludeValidations() {
    final var expectedValidations = List.of(
        ElementValidationDate.builder()
            .mandatory(true)
            .max(Period.ofDays(0))
            .build()
    );

    final var element = QuestionBaseratPaDatum.questionBaseratPaDatum();

    assertEquals(expectedValidations, element.validations());
  }

  @Nested
  class ShouldValidate {

    @Test
    void shallReturnTrueIfCodeIsUndersokning() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("2"))
              .value(
                  ElementValueCode.builder()
                      .codeId(new FieldId("undersokn"))
                      .build()
              )
              .build()
      );

      final var element = QuestionBaseratPaDatum.questionBaseratPaDatum();

      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();

      assertTrue(shouldValidate.test(elementData));
    }

    @Test
    void shallReturnTrueIfCodeIsDistanskontakt() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("2"))
              .value(
                  ElementValueCode.builder()
                      .codeId(new FieldId("distkont"))
                      .build()
              )
              .build()
      );

      final var element = QuestionBaseratPaDatum.questionBaseratPaDatum();

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

      final var element = QuestionBaseratPaDatum.questionBaseratPaDatum();

      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }

    @Test
    void shallReturnFalseIfElementCodeIsNotUndersokningOrDistanskontakt() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("2"))
              .value(
                  ElementValueCode.builder()
                      .codeId(new FieldId("TEST"))
                      .build()
              )
              .build()
      );

      final var element = QuestionBaseratPaDatum.questionBaseratPaDatum();

      final var shouldValidate = element.elementSpecification(ELEMENT_ID).shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }
  }
}