package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements;

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

class QuestionSymptomTest {

  private static final ElementId ELEMENT_ID = new ElementId("58.2");

  @Test
  void shallIncludeId() {
    final var element = QuestionSymptom.questionSymptom();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationTextArea.builder()
        .name("Fyll i vilka symptom barnet har om diagnos inte är fastställd")
        .id(new FieldId("58.2"))
        .build();

    final var element = QuestionSymptom.questionSymptom();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shallIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleLimit.builder()
            .id(new ElementId("58.2"))
            .type(ElementRuleType.TEXT_LIMIT)
            .limit(new RuleLimit((short) 4000))
            .build()
    );

    final var element = QuestionSymptom.questionSymptom();

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shallIncludeValidations() {
    final var expectedValidations = List.of(
        ElementValidationText.builder()
            .limit(4000)
            .build()
    );

    final var element = QuestionSymptom.questionSymptom();

    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shallIncludeWhenRenewingTrue() {
    final var element = QuestionSymptom.questionSymptom();

    assertEquals(Boolean.TRUE, element.includeWhenRenewing());
  }


}