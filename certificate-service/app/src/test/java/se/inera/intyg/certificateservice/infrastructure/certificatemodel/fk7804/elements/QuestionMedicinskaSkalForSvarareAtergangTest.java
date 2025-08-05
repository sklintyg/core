package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements;

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
    final var element = QuestionMedicinskaSkalForSvarareAtergang.questionMedicinskaSkalForSvarareAtergang();

    assertEquals(ElementRuleType.MANDATORY, element.rules().get(0).type());
    assertEquals(ElementRuleType.TEXT_LIMIT, element.rules().get(1).type());
    assertEquals(new RuleLimit((short) 4000), ((ElementRuleLimit) element.rules().get(1)).limit());
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
}

