package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemPositionHearingAid.BADA_ORONEN;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemPositionHearingAid.HOGER;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemPositionHearingAid.VANSTER;

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

class QuestionHorselhjalpmedelPositionTest {

  private static final ElementId ELEMENT_ID = new ElementId("9.3");

  @Test
  void shallIncludeId() {
    final var element = QuestionHorselhjalpmedelPosition.questionHorselhjalpmedelPosition();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationRadioMultipleCode.builder()
        .id(new FieldId("9.3"))
        .name(
            "Om personen behöver använda hörapparat, ange på vilket öra eller om hörapparat används på båda öronen")
        .elementLayout(ElementLayout.ROWS)
        .list(
            List.of(
                new ElementConfigurationCode(
                    new FieldId(HOGER.code()),
                    HOGER.displayName(),
                    HOGER
                ),
                new ElementConfigurationCode(
                    new FieldId(VANSTER.code()),
                    VANSTER.displayName(),
                    VANSTER
                ),
                new ElementConfigurationCode(
                    new FieldId(BADA_ORONEN.code()),
                    BADA_ORONEN.displayName(),
                    BADA_ORONEN
                )
            )
        )
        .build();

    final var element = QuestionHorselhjalpmedelPosition.questionHorselhjalpmedelPosition();

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
                    "exists($HOGER) || exists($VANSTER) || exists($BADA_ORONEN)"
                )
            )
            .build(),
        ElementRuleExpression.builder()
            .id(new ElementId("9.2"))
            .type(ElementRuleType.SHOW)
            .expression(new RuleExpression("$9.2"))
            .build()
    );

    final var element = QuestionHorselhjalpmedelPosition.questionHorselhjalpmedelPosition();

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shallIncludeValidations() {
    final var expectedValidations = List.of(
        ElementValidationCode.builder()
            .mandatory(true)
            .build()
    );

    final var element = QuestionHorselhjalpmedelPosition.questionHorselhjalpmedelPosition();

    assertEquals(expectedValidations, element.validations());
  }
}