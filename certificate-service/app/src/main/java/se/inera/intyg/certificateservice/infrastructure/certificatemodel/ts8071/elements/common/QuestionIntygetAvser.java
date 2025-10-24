package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

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
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvIntygetGallerFor;

public class QuestionIntygetAvser {

  public static final ElementId QUESTION_INTYGET_AVSER_ID = new ElementId("1");
  public static final FieldId QUESTION_INTYGET_AVSER_FIELD_ID = new FieldId("1.1");

  private QuestionIntygetAvser() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionIntygetAvser() {
    final var checkboxes = List.of(
        CodeFactory.elementConfigurationCode(CodeSystemKvIntygetGallerFor.GR_II),
        CodeFactory.elementConfigurationCode(CodeSystemKvIntygetGallerFor.GR_II_III),
        CodeFactory.elementConfigurationCode(CodeSystemKvIntygetGallerFor.FORLANG_GR_II),
        CodeFactory.elementConfigurationCode(CodeSystemKvIntygetGallerFor.FORLANG_GR_II_III),
        CodeFactory.elementConfigurationCode(CodeSystemKvIntygetGallerFor.UTLANDSKT),
        CodeFactory.elementConfigurationCode(CodeSystemKvIntygetGallerFor.TAXI),
        CodeFactory.elementConfigurationCode(CodeSystemKvIntygetGallerFor.ANNAT)
    );

    return ElementSpecification.builder()
        .id(QUESTION_INTYGET_AVSER_ID)
        .configuration(
            ElementConfigurationCheckboxMultipleCode.builder()
                .id(QUESTION_INTYGET_AVSER_FIELD_ID)
                .name("Intyget avser")
                .elementLayout(ElementLayout.ROWS)
                .list(checkboxes)
                .message(
                    ElementMessage.builder()
                        .level(MessageLevel.OBSERVE)
                        .includedForStatuses(List.of(Status.DRAFT))
                        .content(
                            "Välj \"ansökan om taxiförarlegitimation\" endast om personen saknar taxiförarlegitimation och ansöker om en sådan i samband med detta intyg.")
                        .build()
                )
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatoryOrExist(
                    QUESTION_INTYGET_AVSER_ID,
                    List.of(
                        new FieldId(CodeSystemKvIntygetGallerFor.GR_II.code()),
                        new FieldId(CodeSystemKvIntygetGallerFor.GR_II_III.code()),
                        new FieldId(CodeSystemKvIntygetGallerFor.FORLANG_GR_II.code()),
                        new FieldId(CodeSystemKvIntygetGallerFor.FORLANG_GR_II_III.code()),
                        new FieldId(CodeSystemKvIntygetGallerFor.UTLANDSKT.code()),
                        new FieldId(CodeSystemKvIntygetGallerFor.TAXI.code()),
                        new FieldId(CodeSystemKvIntygetGallerFor.ANNAT.code())
                    )
                ),
                CertificateElementRuleFactory.disableSubElements(
                    QUESTION_INTYGET_AVSER_ID,
                    List.of(new FieldId(CodeSystemKvIntygetGallerFor.GR_II.code())),
                    List.of(
                        new FieldId(CodeSystemKvIntygetGallerFor.GR_II_III.code()),
                        new FieldId(CodeSystemKvIntygetGallerFor.FORLANG_GR_II.code()),
                        new FieldId(CodeSystemKvIntygetGallerFor.FORLANG_GR_II_III.code()),
                        new FieldId(CodeSystemKvIntygetGallerFor.UTLANDSKT.code()),
                        new FieldId(CodeSystemKvIntygetGallerFor.ANNAT.code())
                    )
                ),
                CertificateElementRuleFactory.disableSubElements(
                    QUESTION_INTYGET_AVSER_ID,
                    List.of(new FieldId(CodeSystemKvIntygetGallerFor.GR_II_III.code())),
                    List.of(
                        new FieldId(CodeSystemKvIntygetGallerFor.GR_II.code()),
                        new FieldId(CodeSystemKvIntygetGallerFor.FORLANG_GR_II.code()),
                        new FieldId(CodeSystemKvIntygetGallerFor.FORLANG_GR_II_III.code()),
                        new FieldId(CodeSystemKvIntygetGallerFor.UTLANDSKT.code()),
                        new FieldId(CodeSystemKvIntygetGallerFor.ANNAT.code())
                    )
                ),
                CertificateElementRuleFactory.disableSubElements(
                    QUESTION_INTYGET_AVSER_ID,
                    List.of(new FieldId(CodeSystemKvIntygetGallerFor.FORLANG_GR_II.code())),
                    List.of(
                        new FieldId(CodeSystemKvIntygetGallerFor.GR_II.code()),
                        new FieldId(CodeSystemKvIntygetGallerFor.GR_II_III.code()),
                        new FieldId(CodeSystemKvIntygetGallerFor.FORLANG_GR_II_III.code()),
                        new FieldId(CodeSystemKvIntygetGallerFor.UTLANDSKT.code()),
                        new FieldId(CodeSystemKvIntygetGallerFor.ANNAT.code())
                    )
                ),
                CertificateElementRuleFactory.disableSubElements(
                    QUESTION_INTYGET_AVSER_ID,
                    List.of(new FieldId(CodeSystemKvIntygetGallerFor.FORLANG_GR_II_III.code())),
                    List.of(
                        new FieldId(CodeSystemKvIntygetGallerFor.GR_II.code()),
                        new FieldId(CodeSystemKvIntygetGallerFor.GR_II_III.code()),
                        new FieldId(CodeSystemKvIntygetGallerFor.FORLANG_GR_II.code()),
                        new FieldId(CodeSystemKvIntygetGallerFor.UTLANDSKT.code()),
                        new FieldId(CodeSystemKvIntygetGallerFor.ANNAT.code())
                    )
                ),
                CertificateElementRuleFactory.disableSubElements(
                    QUESTION_INTYGET_AVSER_ID,
                    List.of(new FieldId(CodeSystemKvIntygetGallerFor.UTLANDSKT.code())),
                    List.of(
                        new FieldId(CodeSystemKvIntygetGallerFor.GR_II.code()),
                        new FieldId(CodeSystemKvIntygetGallerFor.GR_II_III.code()),
                        new FieldId(CodeSystemKvIntygetGallerFor.FORLANG_GR_II.code()),
                        new FieldId(CodeSystemKvIntygetGallerFor.FORLANG_GR_II_III.code()),
                        new FieldId(CodeSystemKvIntygetGallerFor.ANNAT.code())
                    )
                ),
                CertificateElementRuleFactory.disableSubElements(
                    QUESTION_INTYGET_AVSER_ID,
                    List.of(new FieldId(CodeSystemKvIntygetGallerFor.ANNAT.code())),
                    List.of(
                        new FieldId(CodeSystemKvIntygetGallerFor.GR_II.code()),
                        new FieldId(CodeSystemKvIntygetGallerFor.GR_II_III.code()),
                        new FieldId(CodeSystemKvIntygetGallerFor.FORLANG_GR_II.code()),
                        new FieldId(CodeSystemKvIntygetGallerFor.FORLANG_GR_II_III.code()),
                        new FieldId(CodeSystemKvIntygetGallerFor.UTLANDSKT.code())
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