package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleLimit;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleLimit;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;

class QuestionOvrigBeskrivningTest {

  private static final ElementId ELEMENT_ID = new ElementId("22");

  @Test
  void shallIncludeId() {
    final var element = QuestionOvrigBeskrivning.questionOvrigBeskrivning();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationTextArea.builder()
        .name("Ange övriga upplysningar")
        .id(new FieldId("22.1"))
        .build();

    final var element = QuestionOvrigBeskrivning.questionOvrigBeskrivning();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shallIncludeValidation() {
    final var expectedValidations = List.of(
        ElementValidationText.builder()
            .limit(400)
            .build()
    );

    final var element = QuestionOvrigBeskrivning.questionOvrigBeskrivning();

    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shallIncludeRules() {
    final var expected = List.of(
        ElementRuleLimit.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.TEXT_LIMIT)
            .limit(new RuleLimit((short) 400))
            .build()
    );

    final var element = QuestionOvrigBeskrivning.questionOvrigBeskrivning();

    assertEquals(expected, element.rules());
  }
}