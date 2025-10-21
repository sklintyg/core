package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvTs002.NO;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvTs002.NO_DECISION;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvTs002.YES;

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

class QuestionBedomningTest {

  private static final ElementId ELEMENT_ID = new ElementId("23");

  @Test
  void shallIncludeId() {
    final var element = QuestionBedomning.questionBedomning();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationRadioMultipleCode.builder()
        .id(new FieldId("23.1"))
        .name(
            "Bedöms personen utifrån Transportstyrelsens föreskrifter och allmänna råd (TSFS 2010:125) om medicinska krav för innehav av körkort m.m. ha en sjukdom eller medicinskt tillstånd som innebär en trafiksäkerhetsrisk?")
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
                    new FieldId(NO_DECISION.code()),
                    NO_DECISION.displayName(),
                    NO_DECISION
                )
            )
        )
        .build();

    final var element = QuestionBedomning.questionBedomning();

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
                    "exists($ja) || exists($nej) || exists($ejstalln)"
                )
            )
            .build()
    );

    final var element = QuestionBedomning.questionBedomning();

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shallIncludeValidations() {
    final var expectedValidations = List.of(
        ElementValidationCode.builder()
            .mandatory(true)
            .build()
    );

    final var element = QuestionBedomning.questionBedomning();

    assertEquals(expectedValidations, element.validations());
  }
}