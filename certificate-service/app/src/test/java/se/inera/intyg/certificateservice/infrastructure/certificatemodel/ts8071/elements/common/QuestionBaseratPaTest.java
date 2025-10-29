package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvInformationskallaForIntyg.DISTANSKONTAKT;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvInformationskallaForIntyg.JOURNALUPPGIFTER;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvInformationskallaForIntyg.UNDERSOKNING;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementLayout;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationCode;

class QuestionBaseratPaTest {

  private static final ElementId ELEMENT_ID = new ElementId("2");

  @Test
  void shallIncludeId() {
    final var element = QuestionBaseratPa.questionBaseratPa();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationRadioMultipleCode.builder()
        .id(new FieldId("2.1"))
        .name("Intyget är baserat på")
        .elementLayout(ElementLayout.ROWS)
        .list(
            List.of(
                new ElementConfigurationCode(
                    new FieldId(JOURNALUPPGIFTER.code()),
                    JOURNALUPPGIFTER.displayName(),
                    JOURNALUPPGIFTER
                ),
                new ElementConfigurationCode(
                    new FieldId(DISTANSKONTAKT.code()),
                    DISTANSKONTAKT.displayName(),
                    DISTANSKONTAKT
                ),
                new ElementConfigurationCode(
                    new FieldId(UNDERSOKNING.code()),
                    UNDERSOKNING.displayName(),
                    UNDERSOKNING
                )
            )
        )
        .build();

    final var element = QuestionBaseratPa.questionBaseratPa();

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
                    "exists($journal) || exists($distkont) || exists($undersokn)"
                )
            )
            .build()
    );

    final var element = QuestionBaseratPa.questionBaseratPa();

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shallIncludeValidations() {
    final var expectedValidations = List.of(
        ElementValidationCode.builder()
            .mandatory(true)
            .build()
    );

    final var element = QuestionBaseratPa.questionBaseratPa();

    assertEquals(expectedValidations, element.validations());
  }
}