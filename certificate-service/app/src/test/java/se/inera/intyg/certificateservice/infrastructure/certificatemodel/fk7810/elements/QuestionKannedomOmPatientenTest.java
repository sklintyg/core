package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionKannedomOmPatienten.questionKannedomOmPatienten;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDropdownCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationCode;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0010;

class QuestionKannedomOmPatientenTest {

  private static final ElementId ELEMENT_ID = new ElementId("2");

  @Test
  void shallIncludeId() {
    final var element = questionKannedomOmPatienten();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var dropdownItems = List.of(
        new ElementConfigurationCode(
            new FieldId(""),
            "Välj i listan",
            null
        ),
        new ElementConfigurationCode(
            new FieldId(CodeSystemKvFkmu0010.INGEN_TIDIGARE.code()),
            CodeSystemKvFkmu0010.INGEN_TIDIGARE.displayName(),
            CodeSystemKvFkmu0010.INGEN_TIDIGARE
        ),
        new ElementConfigurationCode(
            new FieldId(CodeSystemKvFkmu0010.MINDRE_AN_ETT_AR.code()),
            CodeSystemKvFkmu0010.MINDRE_AN_ETT_AR.displayName(),
            CodeSystemKvFkmu0010.MINDRE_AN_ETT_AR
        ),
        new ElementConfigurationCode(
            new FieldId(CodeSystemKvFkmu0010.MER_AN_ETT_AR.code()),
            CodeSystemKvFkmu0010.MER_AN_ETT_AR.displayName(),
            CodeSystemKvFkmu0010.MER_AN_ETT_AR
        )
    );
    final var expectedConfiguration = ElementConfigurationDropdownCode.builder()
        .id(new FieldId("2.2"))
        .name("Jag har kännedom om patienten sedan")
        .list(dropdownItems)
        .build();

    final var element = questionKannedomOmPatienten();

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
                    "exists($" + CodeSystemKvFkmu0010.INGEN_TIDIGARE.code() + ") || exists($"
                        + CodeSystemKvFkmu0010.MINDRE_AN_ETT_AR.code() + ") || exists($"
                        + CodeSystemKvFkmu0010.MER_AN_ETT_AR.code() + ")"
                )
            )
            .build()
    );

    final var element = questionKannedomOmPatienten();

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shallIncludeValidation() {
    final var expectedValidations = List.of(
        ElementValidationCode.builder()
            .mandatory(true)
            .build()
    );

    final var element = questionKannedomOmPatienten();

    assertEquals(expectedValidations, element.validations());
  }

  @Test
  void shallNotBeIncludedWhenRenewing() {
    assertFalse(questionKannedomOmPatienten().includeWhenRenewing());
  }

  @Test
  void shouldHaveIncludeWhenRenewingFalse() {
    final var element = questionKannedomOmPatienten();
    assertFalse(element.includeWhenRenewing());
  }
}