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

  public static final ElementId INTYGET_AVSER_ID = new ElementId("1");
  public static final FieldId INTYGET_AVSER_FIELD_ID = new FieldId("1.1");

  private QuestionIntygetAvser() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionIntygetAvser() {
    final var checkboxes = List.of(
        CodeFactory.elementConfigurationCode(CodeSystemKvTs0002.G2),
        CodeFactory.elementConfigurationCode(CodeSystemKvTs0002.G23),
        CodeFactory.elementConfigurationCode(CodeSystemKvTs0002.GH2),
        CodeFactory.elementConfigurationCode(CodeSystemKvTs0002.GH23),
        CodeFactory.elementConfigurationCode(CodeSystemKvTs0002.UTLANDSKT),
        CodeFactory.elementConfigurationCode(CodeSystemKvTs0002.TAXI),
        CodeFactory.elementConfigurationCode(CodeSystemKvTs0002.ANNAT)
    );

    return ElementSpecification.builder()
        .id(INTYGET_AVSER_ID)
        .configuration(
            ElementConfigurationCheckboxMultipleCode.builder()
                .id(INTYGET_AVSER_FIELD_ID)
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
                    INTYGET_AVSER_ID,
                    List.of(
                        new FieldId(CodeSystemKvTs0002.G2.code()),
                        new FieldId(CodeSystemKvTs0002.G23.code()),
                        new FieldId(CodeSystemKvTs0002.GH2.code()),
                        new FieldId(CodeSystemKvTs0002.GH23.code()),
                        new FieldId(CodeSystemKvTs0002.UTLANDSKT.code()),
                        new FieldId(CodeSystemKvTs0002.TAXI.code()),
                        new FieldId(CodeSystemKvTs0002.ANNAT.code())
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
