package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementLayout;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMessage;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.MessageLevel;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationCodeList;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvTs0002;

public class QuestionIntygetAvser {

  public static final ElementId QUESTION_INTYGET_AVSER_ID = new ElementId("1");
  public static final FieldId QUESTION_INTYGET_AVSER_FIELD_ID = new FieldId("1.1");

  private QuestionIntygetAvser() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionIntygetAvser() {
    final var checkboxes = List.of(
        CodeFactory.elementConfigurationCode(CodeSystemKvTs0002.GR_II),
        CodeFactory.elementConfigurationCode(CodeSystemKvTs0002.GR_II_III),
        CodeFactory.elementConfigurationCode(CodeSystemKvTs0002.FORLANG_GR_II),
        CodeFactory.elementConfigurationCode(CodeSystemKvTs0002.FORLANG_GR_II_III),
        CodeFactory.elementConfigurationCode(CodeSystemKvTs0002.UTLANDSKT),
        CodeFactory.elementConfigurationCode(CodeSystemKvTs0002.TAXI),
        CodeFactory.elementConfigurationCode(CodeSystemKvTs0002.ANNAT)
    );

    return ElementSpecification.builder()
        .id(QUESTION_INTYGET_AVSER_ID)
        .configuration(
            ElementConfigurationCheckboxMultipleCode.builder()
                .id(QUESTION_INTYGET_AVSER_FIELD_ID)
                .name("Intyget avser")
                .elementLayout(ElementLayout.COLUMNS)
                .list(checkboxes)
                .message(
                    ElementMessage.builder()
                        .level(MessageLevel.INFO)
                        .includedForStatuses(List.of(Status.DRAFT))
                        .content(
                            "Endast ett alternativ kan väljas. Undantaget är om intyget avser taxiförarlegitimation, då kan två val göras.")
                        .build()
                )
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    QUESTION_INTYGET_AVSER_ID,
                    List.of(
                        new FieldId(CodeSystemKvTs0002.GR_II.code()),
                        new FieldId(CodeSystemKvTs0002.GR_II_III.code()),
                        new FieldId(CodeSystemKvTs0002.FORLANG_GR_II.code()),
                        new FieldId(CodeSystemKvTs0002.FORLANG_GR_II_III.code()),
                        new FieldId(CodeSystemKvTs0002.UTLANDSKT.code()),
                        new FieldId(CodeSystemKvTs0002.TAXI.code()),
                        new FieldId(CodeSystemKvTs0002.ANNAT.code())
                    )
                ),
                CertificateElementRuleFactory.disableSubElements(
                    QUESTION_INTYGET_AVSER_ID,
                    List.of(new FieldId(CodeSystemKvTs0002.GR_II.code())),
                    List.of(
                        new FieldId(CodeSystemKvTs0002.GR_II_III.code()),
                        new FieldId(CodeSystemKvTs0002.FORLANG_GR_II.code()),
                        new FieldId(CodeSystemKvTs0002.FORLANG_GR_II_III.code()),
                        new FieldId(CodeSystemKvTs0002.UTLANDSKT.code()),
                        new FieldId(CodeSystemKvTs0002.ANNAT.code())
                    )
                ),
                CertificateElementRuleFactory.disableSubElements(
                    QUESTION_INTYGET_AVSER_ID,
                    List.of(new FieldId(CodeSystemKvTs0002.GR_II_III.code())),
                    List.of(
                        new FieldId(CodeSystemKvTs0002.GR_II.code()),
                        new FieldId(CodeSystemKvTs0002.FORLANG_GR_II.code()),
                        new FieldId(CodeSystemKvTs0002.FORLANG_GR_II_III.code()),
                        new FieldId(CodeSystemKvTs0002.UTLANDSKT.code()),
                        new FieldId(CodeSystemKvTs0002.ANNAT.code())
                    )
                ),
                CertificateElementRuleFactory.disableSubElements(
                    QUESTION_INTYGET_AVSER_ID,
                    List.of(new FieldId(CodeSystemKvTs0002.FORLANG_GR_II.code())),
                    List.of(
                        new FieldId(CodeSystemKvTs0002.GR_II.code()),
                        new FieldId(CodeSystemKvTs0002.GR_II_III.code()),
                        new FieldId(CodeSystemKvTs0002.FORLANG_GR_II_III.code()),
                        new FieldId(CodeSystemKvTs0002.UTLANDSKT.code()),
                        new FieldId(CodeSystemKvTs0002.ANNAT.code())
                    )
                ),
                CertificateElementRuleFactory.disableSubElements(
                    QUESTION_INTYGET_AVSER_ID,
                    List.of(new FieldId(CodeSystemKvTs0002.FORLANG_GR_II_III.code())),
                    List.of(
                        new FieldId(CodeSystemKvTs0002.GR_II.code()),
                        new FieldId(CodeSystemKvTs0002.GR_II_III.code()),
                        new FieldId(CodeSystemKvTs0002.FORLANG_GR_II.code()),
                        new FieldId(CodeSystemKvTs0002.UTLANDSKT.code()),
                        new FieldId(CodeSystemKvTs0002.ANNAT.code())
                    )
                ),
                CertificateElementRuleFactory.disableSubElements(
                    QUESTION_INTYGET_AVSER_ID,
                    List.of(new FieldId(CodeSystemKvTs0002.UTLANDSKT.code())),
                    List.of(
                        new FieldId(CodeSystemKvTs0002.GR_II.code()),
                        new FieldId(CodeSystemKvTs0002.GR_II_III.code()),
                        new FieldId(CodeSystemKvTs0002.FORLANG_GR_II.code()),
                        new FieldId(CodeSystemKvTs0002.FORLANG_GR_II_III.code()),
                        new FieldId(CodeSystemKvTs0002.ANNAT.code())
                    )
                ),
                CertificateElementRuleFactory.disableSubElements(
                    QUESTION_INTYGET_AVSER_ID,
                    List.of(new FieldId(CodeSystemKvTs0002.ANNAT.code())),
                    List.of(
                        new FieldId(CodeSystemKvTs0002.GR_II.code()),
                        new FieldId(CodeSystemKvTs0002.GR_II_III.code()),
                        new FieldId(CodeSystemKvTs0002.FORLANG_GR_II.code()),
                        new FieldId(CodeSystemKvTs0002.FORLANG_GR_II_III.code()),
                        new FieldId(CodeSystemKvTs0002.UTLANDSKT.code())
                    )
                )
            )
        )
        .validations(
            List.of(
                ElementValidationCodeList.builder()
                    .mandatory(true)
                    .build()
            )
        )
        .build();
  }
}

