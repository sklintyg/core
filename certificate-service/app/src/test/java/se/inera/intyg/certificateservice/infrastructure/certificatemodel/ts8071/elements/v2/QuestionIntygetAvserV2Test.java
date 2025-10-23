package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementLayout;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMessage;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.MessageLevel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationCodeList;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvIntygetGallerFor;

class QuestionIntygetAvserV2Test {

  private static final ElementId ELEMENT_ID = new ElementId("1");

  @Test
  void shouldIncludeId() {
    final var element = QuestionIntygetAvserV2.questionIntygetAvserV2();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shouldIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCheckboxMultipleCode.builder()
        .id(new FieldId("1.1"))
        .name("Intyget avser")
        .elementLayout(ElementLayout.ROWS)
        .list(
            List.of(
                CodeFactory.elementConfigurationCode(CodeSystemKvIntygetGallerFor.GR_II),
                CodeFactory.elementConfigurationCode(CodeSystemKvIntygetGallerFor.GR_II_III),
                CodeFactory.elementConfigurationCode(CodeSystemKvIntygetGallerFor.FORLANG_GR_II),
                CodeFactory.elementConfigurationCode(
                    CodeSystemKvIntygetGallerFor.FORLANG_GR_II_III),
                CodeFactory.elementConfigurationCode(CodeSystemKvIntygetGallerFor.UTLANDSKT),
                CodeFactory.elementConfigurationCode(CodeSystemKvIntygetGallerFor.TAXI),
                CodeFactory.elementConfigurationCode(CodeSystemKvIntygetGallerFor.ANNAT)
            )
        )
        .message(
            ElementMessage.builder()
                .level(MessageLevel.OBSERVE)
                .includedForStatuses(List.of(Status.DRAFT))
                .content(
                    "Endast ett alternativ kan väljas. Undantaget är om intyget avser taxiförarlegitimation, då kan två val göras.")
                .build()
        )
        .build();

    final var element = QuestionIntygetAvserV2.questionIntygetAvserV2();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shouldIncludeRules() {
    final var expectedRules = List.of(
        ElementRuleExpression.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.MANDATORY)
            .expression(
                new RuleExpression(
                    "exists($GR_II) || exists($GR_II_III) || exists($FORLANG_GR_II) || exists($FORLANG_GR_II_III) || exists($UTLANDSKT) || exists($TAXI) || exists($ANNAT)"
                )
            )
            .build(),
        ElementRuleExpression.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.DISABLE_SUB_ELEMENT)
            .expression(
                new RuleExpression(
                    "$GR_II"
                )
            )
            .affectedSubElements(
                List.of(
                    new FieldId(CodeSystemKvIntygetGallerFor.GR_II_III.code()),
                    new FieldId(CodeSystemKvIntygetGallerFor.FORLANG_GR_II.code()),
                    new FieldId(CodeSystemKvIntygetGallerFor.FORLANG_GR_II_III.code()),
                    new FieldId(CodeSystemKvIntygetGallerFor.UTLANDSKT.code()),
                    new FieldId(CodeSystemKvIntygetGallerFor.ANNAT.code())
                )
            )
            .build(),
        ElementRuleExpression.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.DISABLE_SUB_ELEMENT)
            .expression(
                new RuleExpression(
                    "$GR_II_III"
                )
            )
            .affectedSubElements(
                List.of(
                    new FieldId(CodeSystemKvIntygetGallerFor.GR_II.code()),
                    new FieldId(CodeSystemKvIntygetGallerFor.FORLANG_GR_II.code()),
                    new FieldId(CodeSystemKvIntygetGallerFor.FORLANG_GR_II_III.code()),
                    new FieldId(CodeSystemKvIntygetGallerFor.UTLANDSKT.code()),
                    new FieldId(CodeSystemKvIntygetGallerFor.ANNAT.code())
                )
            )
            .build(),
        ElementRuleExpression.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.DISABLE_SUB_ELEMENT)
            .expression(
                new RuleExpression(
                    "$FORLANG_GR_II"
                )
            )
            .affectedSubElements(
                List.of(
                    new FieldId(CodeSystemKvIntygetGallerFor.GR_II.code()),
                    new FieldId(CodeSystemKvIntygetGallerFor.GR_II_III.code()),
                    new FieldId(CodeSystemKvIntygetGallerFor.FORLANG_GR_II_III.code()),
                    new FieldId(CodeSystemKvIntygetGallerFor.UTLANDSKT.code()),
                    new FieldId(CodeSystemKvIntygetGallerFor.ANNAT.code())
                )
            )
            .build(),
        ElementRuleExpression.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.DISABLE_SUB_ELEMENT)
            .expression(
                new RuleExpression(
                    "$FORLANG_GR_II_III"
                )
            )
            .affectedSubElements(
                List.of(
                    new FieldId(CodeSystemKvIntygetGallerFor.GR_II.code()),
                    new FieldId(CodeSystemKvIntygetGallerFor.GR_II_III.code()),
                    new FieldId(CodeSystemKvIntygetGallerFor.FORLANG_GR_II.code()),
                    new FieldId(CodeSystemKvIntygetGallerFor.UTLANDSKT.code()),
                    new FieldId(CodeSystemKvIntygetGallerFor.ANNAT.code())
                )
            )
            .build(),
        ElementRuleExpression.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.DISABLE_SUB_ELEMENT)
            .expression(
                new RuleExpression(
                    "$UTLANDSKT"
                )
            )
            .affectedSubElements(
                List.of(
                    new FieldId(CodeSystemKvIntygetGallerFor.GR_II.code()),
                    new FieldId(CodeSystemKvIntygetGallerFor.GR_II_III.code()),
                    new FieldId(CodeSystemKvIntygetGallerFor.FORLANG_GR_II.code()),
                    new FieldId(CodeSystemKvIntygetGallerFor.FORLANG_GR_II_III.code()),
                    new FieldId(CodeSystemKvIntygetGallerFor.ANNAT.code())
                )
            )
            .build(),
        ElementRuleExpression.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.DISABLE_SUB_ELEMENT)
            .expression(
                new RuleExpression(
                    "$ANNAT"
                )
            )
            .affectedSubElements(
                List.of(
                    new FieldId(CodeSystemKvIntygetGallerFor.GR_II.code()),
                    new FieldId(CodeSystemKvIntygetGallerFor.GR_II_III.code()),
                    new FieldId(CodeSystemKvIntygetGallerFor.FORLANG_GR_II.code()),
                    new FieldId(CodeSystemKvIntygetGallerFor.FORLANG_GR_II_III.code()),
                    new FieldId(CodeSystemKvIntygetGallerFor.UTLANDSKT.code()),
                    new FieldId(CodeSystemKvIntygetGallerFor.ANNAT.code())
                )
            )
            .build()
    );

    final var element = QuestionIntygetAvserV2.questionIntygetAvserV2();

    assertEquals(expectedRules, element.rules());
  }

  @Test
  void shouldIncludeValidation() {
    final var expectedValidations = List.of(
        ElementValidationCodeList.builder()
            .mandatory(true)
            .build()
    );

    final var element = QuestionIntygetAvserV2.questionIntygetAvserV2();

    assertEquals(expectedValidations, element.validations());
  }
}

