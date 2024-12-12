package se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements;

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
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvTs0002;

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
                "Endast ett alternativ kan väljas. Undantaget är om intyget avser taxiförarlegitimation, då kan två val göras.")
            .build())
        .list(
            List.of(
                new ElementConfigurationCode(
                    new FieldId(CodeSystemKvTs0002.GR_II.code()),
                    CodeSystemKvTs0002.GR_II.displayName(),
                    CodeSystemKvTs0002.GR_II
                ),
                new ElementConfigurationCode(
                    new FieldId(CodeSystemKvTs0002.GR_II_III.code()),
                    CodeSystemKvTs0002.GR_II_III.displayName(),
                    CodeSystemKvTs0002.GR_II_III
                ),
                new ElementConfigurationCode(
                    new FieldId(CodeSystemKvTs0002.FORLANG_GR_II.code()),
                    CodeSystemKvTs0002.FORLANG_GR_II.displayName(),
                    CodeSystemKvTs0002.FORLANG_GR_II
                ),
                new ElementConfigurationCode(
                    new FieldId(CodeSystemKvTs0002.FORLANG_GR_II_III.code()),
                    CodeSystemKvTs0002.FORLANG_GR_II_III.displayName(),
                    CodeSystemKvTs0002.FORLANG_GR_II_III
                ),
                new ElementConfigurationCode(
                    new FieldId(CodeSystemKvTs0002.UTLANDSKT.code()),
                    CodeSystemKvTs0002.UTLANDSKT.displayName(),
                    CodeSystemKvTs0002.UTLANDSKT
                ),
                new ElementConfigurationCode(
                    new FieldId(CodeSystemKvTs0002.TAXI.code()),
                    CodeSystemKvTs0002.TAXI.displayName(),
                    CodeSystemKvTs0002.TAXI
                ),
                new ElementConfigurationCode(
                    new FieldId(CodeSystemKvTs0002.ANNAT.code()),
                    CodeSystemKvTs0002.ANNAT.displayName(),
                    CodeSystemKvTs0002.ANNAT
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
                    new FieldId(CodeSystemKvTs0002.GR_II_III.code()),
                    new FieldId(CodeSystemKvTs0002.FORLANG_GR_II.code()),
                    new FieldId(CodeSystemKvTs0002.FORLANG_GR_II_III.code()),
                    new FieldId(CodeSystemKvTs0002.UTLANDSKT.code()),
                    new FieldId(CodeSystemKvTs0002.ANNAT.code())
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
                    new FieldId(CodeSystemKvTs0002.GR_II.code()),
                    new FieldId(CodeSystemKvTs0002.FORLANG_GR_II.code()),
                    new FieldId(CodeSystemKvTs0002.FORLANG_GR_II_III.code()),
                    new FieldId(CodeSystemKvTs0002.UTLANDSKT.code()),
                    new FieldId(CodeSystemKvTs0002.ANNAT.code())
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
                    new FieldId(CodeSystemKvTs0002.GR_II.code()),
                    new FieldId(CodeSystemKvTs0002.GR_II_III.code()),
                    new FieldId(CodeSystemKvTs0002.FORLANG_GR_II_III.code()),
                    new FieldId(CodeSystemKvTs0002.UTLANDSKT.code()),
                    new FieldId(CodeSystemKvTs0002.ANNAT.code())
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
                    new FieldId(CodeSystemKvTs0002.GR_II.code()),
                    new FieldId(CodeSystemKvTs0002.GR_II_III.code()),
                    new FieldId(CodeSystemKvTs0002.FORLANG_GR_II.code()),
                    new FieldId(CodeSystemKvTs0002.UTLANDSKT.code()),
                    new FieldId(CodeSystemKvTs0002.ANNAT.code())
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
                    new FieldId(CodeSystemKvTs0002.GR_II.code()),
                    new FieldId(CodeSystemKvTs0002.GR_II_III.code()),
                    new FieldId(CodeSystemKvTs0002.FORLANG_GR_II.code()),
                    new FieldId(CodeSystemKvTs0002.FORLANG_GR_II_III.code()),
                    new FieldId(CodeSystemKvTs0002.ANNAT.code())
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
                    new FieldId(CodeSystemKvTs0002.GR_II.code()),
                    new FieldId(CodeSystemKvTs0002.GR_II_III.code()),
                    new FieldId(CodeSystemKvTs0002.FORLANG_GR_II.code()),
                    new FieldId(CodeSystemKvTs0002.FORLANG_GR_II_III.code()),
                    new FieldId(CodeSystemKvTs0002.UTLANDSKT.code())
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