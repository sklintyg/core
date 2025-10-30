package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvIdKontroll.IDK1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvIdKontroll.IDK2;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvIdKontroll.IDK3;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvIdKontroll.IDK4;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvIdKontroll.IDK5;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvIdKontroll.IDK6;

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

class QuestionIdentitetTest {

  private static final ElementId ELEMENT_ID = new ElementId("3");

  @Test
  void shallIncludeId() {
    final var element = QuestionIdentitet.questionIdentitet();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationRadioMultipleCode.builder()
        .id(new FieldId("3.1"))
        .name("Identitet styrkt genom")
        .elementLayout(ElementLayout.COLUMNS)
        .list(
            List.of(
                new ElementConfigurationCode(
                    new FieldId(IDK1.code()),
                    IDK1.displayName(),
                    IDK1
                ),
                new ElementConfigurationCode(
                    new FieldId(IDK2.code()),
                    IDK2.displayName(),
                    IDK2
                ),
                new ElementConfigurationCode(
                    new FieldId(IDK3.code()),
                    IDK3.displayName(),
                    IDK3
                ),
                new ElementConfigurationCode(
                    new FieldId(IDK4.code()),
                    IDK4.displayName(),
                    IDK4
                ),
                new ElementConfigurationCode(
                    new FieldId(IDK5.code()),
                    IDK5.displayName(),
                    IDK5
                ),
                new ElementConfigurationCode(
                    new FieldId(IDK6.code()),
                    IDK6.displayName(),
                    IDK6
                )
            )
        )
        .build();

    final var element = QuestionIdentitet.questionIdentitet();

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
                    "exists($IDK1) || exists($IDK2) || exists($IDK3) || exists($IDK4) || exists($IDK5) || exists($IDK6)"
                )
            )
            .build()
    );

    final var element = QuestionIdentitet.questionIdentitet();

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shallIncludeValidations() {
    final var expectedValidations = List.of(
        ElementValidationCode.builder()
            .mandatory(true)
            .build()
    );

    final var element = QuestionIdentitet.questionIdentitet();

    assertEquals(expectedValidations, element.validations());
  }
}