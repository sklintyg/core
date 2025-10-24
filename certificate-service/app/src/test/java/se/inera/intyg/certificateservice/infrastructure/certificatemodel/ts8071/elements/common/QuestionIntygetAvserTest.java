package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementLayout;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMessage;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.MessageLevel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationCodeList;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvIntygetGallerFor;

class QuestionIntygetAvserTest {

  private static final ElementId ELEMENT_ID = new ElementId("1");

  @Test
  void shallIncludeId() {
    final var element = QuestionIntygetAvser.questionIntygetAvser();

    assertEquals(ELEMENT_ID, element.id());
  }

  @Test
  void shallIncludeConfiguration() {
    final var expectedConfiguration = ElementConfigurationCheckboxMultipleCode.builder()
        .name("Intyget avser")
        .elementLayout(ElementLayout.ROWS)
        .id(new FieldId("1.1"))
        .message(ElementMessage.builder()
            .level(MessageLevel.OBSERVE)
            .includedForStatuses(List.of(Status.DRAFT))
            .content(
                "Välj \"ansökan om taxiförarlegitimation\" endast om personen saknar taxiförarlegitimation och ansöker om en sådan i samband med detta intyg.")
            .build())
        .list(
            List.of(
                new ElementConfigurationCode(
                    new FieldId(CodeSystemKvIntygetGallerFor.GR_II.code()),
                    CodeSystemKvIntygetGallerFor.GR_II.displayName(),
                    CodeSystemKvIntygetGallerFor.GR_II
                ),
                new ElementConfigurationCode(
                    new FieldId(CodeSystemKvIntygetGallerFor.GR_II_III.code()),
                    CodeSystemKvIntygetGallerFor.GR_II_III.displayName(),
                    CodeSystemKvIntygetGallerFor.GR_II_III
                ),
                new ElementConfigurationCode(
                    new FieldId(CodeSystemKvIntygetGallerFor.FORLANG_GR_II.code()),
                    CodeSystemKvIntygetGallerFor.FORLANG_GR_II.displayName(),
                    CodeSystemKvIntygetGallerFor.FORLANG_GR_II
                ),
                new ElementConfigurationCode(
                    new FieldId(CodeSystemKvIntygetGallerFor.FORLANG_GR_II_III.code()),
                    CodeSystemKvIntygetGallerFor.FORLANG_GR_II_III.displayName(),
                    CodeSystemKvIntygetGallerFor.FORLANG_GR_II_III
                ),
                new ElementConfigurationCode(
                    new FieldId(CodeSystemKvIntygetGallerFor.UTLANDSKT.code()),
                    CodeSystemKvIntygetGallerFor.UTLANDSKT.displayName(),
                    CodeSystemKvIntygetGallerFor.UTLANDSKT
                ),
                new ElementConfigurationCode(
                    new FieldId(CodeSystemKvIntygetGallerFor.TAXI.code()),
                    CodeSystemKvIntygetGallerFor.TAXI.displayName(),
                    CodeSystemKvIntygetGallerFor.TAXI
                ),
                new ElementConfigurationCode(
                    new FieldId(CodeSystemKvIntygetGallerFor.ANNAT.code()),
                    CodeSystemKvIntygetGallerFor.ANNAT.displayName(),
                    CodeSystemKvIntygetGallerFor.ANNAT
                )
            )
        ).build();

    final var element = QuestionIntygetAvser.questionIntygetAvser();

    assertEquals(expectedConfiguration, element.configuration());
  }

  @Test
  void shallIncludeRules() {
    final var expectedRule = List.of(
        ElementRuleExpression.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.MANDATORY)
            .expression(
                new RuleExpression(
                    "exists($gr_II) || exists($gr_II_III) || exists($forlang_gr_II) || exists($forlang_gr_II_III) || exists($utbyt_utl_kk) || exists($tax_leg) || exists($int_begar_ts)"
                )
            )
            .build(),
        ElementRuleExpression.builder()
            .id(ELEMENT_ID)
            .type(ElementRuleType.DISABLE_SUB_ELEMENT)
            .expression(
                new RuleExpression(
                    "exists(gr_II)"
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
                    "exists(gr_II_III)"
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
                    "exists(forlang_gr_II)"
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
                    "exists(forlang_gr_II_III)"
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
                    "exists(utbyt_utl_kk)"
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
                    "exists(int_begar_ts)"
                )
            )
            .affectedSubElements(
                List.of(
                    new FieldId(CodeSystemKvIntygetGallerFor.GR_II.code()),
                    new FieldId(CodeSystemKvIntygetGallerFor.GR_II_III.code()),
                    new FieldId(CodeSystemKvIntygetGallerFor.FORLANG_GR_II.code()),
                    new FieldId(CodeSystemKvIntygetGallerFor.FORLANG_GR_II_III.code()),
                    new FieldId(CodeSystemKvIntygetGallerFor.UTLANDSKT.code())
                )
            )
            .build()
    );

    final var element = QuestionIntygetAvser.questionIntygetAvser();

    assertEquals(expectedRule, element.rules());
  }

  @Test
  void shallIncludeValidation() {
    final var expectedValidations = List.of(
        ElementValidationCodeList.builder()
            .mandatory(true)
            .build()
    );

    final var element = QuestionIntygetAvser.questionIntygetAvser();

    assertEquals(expectedValidations, element.validations());
  }
}