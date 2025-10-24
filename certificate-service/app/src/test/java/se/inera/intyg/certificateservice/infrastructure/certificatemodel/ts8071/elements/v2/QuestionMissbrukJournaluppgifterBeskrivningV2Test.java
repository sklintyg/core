package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2;

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
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleLimit;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleLimit;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;

class QuestionMissbrukJournaluppgifterBeskrivningV2Test {

  private static final ElementId ELEMENT_ID = new ElementId("18.4");

  @Test
  void shouldIncludeId() {
    final var element = QuestionMissbrukJournaluppgifterBeskrivningV2.questionMissbrukJournaluppgifterBeskrivningV2();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shouldIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationTextArea.builder()
        .name("Ange vilka uppgifter eller tecken och när det var")
        .id(new FieldId("18.4"))
        .build();

    final var element = QuestionMissbrukJournaluppgifterBeskrivningV2.questionMissbrukJournaluppgifterBeskrivningV2();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shouldIncludeRules() {
    final var element = QuestionMissbrukJournaluppgifterBeskrivningV2.questionMissbrukJournaluppgifterBeskrivningV2();

    assertEquals(3, element.rules().size());
  }

  @Test
  void shouldIncludeTextLimitRule() {
    final var expectedRule = ElementRuleLimit.builder()
        .id(ELEMENT_ID)
        .type(ElementRuleType.TEXT_LIMIT)
        .limit(new RuleLimit((short) 250))
        .build();

    final var element = QuestionMissbrukJournaluppgifterBeskrivningV2.questionMissbrukJournaluppgifterBeskrivningV2();

    assertEquals(expectedRule, element.rules().get(2));
  }

  @Test
  void shouldIncludeValidations() {
    final var expectedValidations = List.of(
        ElementValidationText.builder()
            .mandatory(true)
            .limit(250)
            .build()
    );

    final var element = QuestionMissbrukJournaluppgifterBeskrivningV2.questionMissbrukJournaluppgifterBeskrivningV2();

    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shouldIncludeMapping() {
    final var element = QuestionMissbrukJournaluppgifterBeskrivningV2.questionMissbrukJournaluppgifterBeskrivningV2();

    assertEquals(new ElementMapping(QuestionMissbrukV2.QUESTION_MISSBRUK_V2_ID, null),
        element.mapping());
  }

  @Nested
  class ShouldValidate {

    @Test
    void shouldReturnTrueIfBooleanIsTrue() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("18.3"))
              .value(
                  ElementValueBoolean.builder()
                      .value(true)
                      .build()
              )
              .build()
      );

      final var element = QuestionMissbrukJournaluppgifterBeskrivningV2.questionMissbrukJournaluppgifterBeskrivningV2();

      final var shouldValidate = element.shouldValidate();

      assertTrue(shouldValidate.test(elementData));
    }

    @Test
    void shouldReturnFalseIfElementMissing() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("18.2"))
              .value(
                  ElementValueBoolean.builder()
                      .value(true)
                      .build()
              )
              .build()
      );

      final var element = QuestionMissbrukJournaluppgifterBeskrivningV2.questionMissbrukJournaluppgifterBeskrivningV2();

      final var shouldValidate = element.shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }

    @Test
    void shouldReturnFalseIfElementFalse() {
      final var elementData = List.of(
          ElementData.builder()
              .id(new ElementId("18.3"))
              .value(
                  ElementValueBoolean.builder()
                      .value(false)
                      .build()
              )
              .build()
      );

      final var element = QuestionMissbrukJournaluppgifterBeskrivningV2.questionMissbrukJournaluppgifterBeskrivningV2();

      final var shouldValidate = element.shouldValidate();

      assertFalse(shouldValidate.test(elementData));
    }
  }
}

