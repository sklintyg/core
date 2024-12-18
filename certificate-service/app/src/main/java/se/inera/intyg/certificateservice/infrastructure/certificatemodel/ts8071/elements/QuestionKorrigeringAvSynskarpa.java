package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory.lessThanOrEqual;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory.multipleAndExpressions;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory.multipleOrExpression;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory.singleExpression;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory.withCitation;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory.wrapWithParenthesis;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionSynskarpa.LEFT_EYE_WITH_CORRECTION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionSynskarpa.QUESTION_SYNSKARPA_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionSynskarpa.RIGHT_EYE_WITH_CORRECTION_ID;

import java.util.List;
import java.util.Optional;
import se.inera.intyg.certificateservice.domain.certificate.model.CodeListToBoolean;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueVisualAcuities;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementLayout;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationCodeList;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKorrigeringAvSynskarpa;

public class QuestionKorrigeringAvSynskarpa {

  public static final ElementId QUESTION_KORRIGERING_AV_SYNSKARPA_ID = new ElementId("6");
  public static final FieldId QUESTION_KORRIGERING_AV_SYNSKARPA_FIELD_ID = new FieldId("6");

  private QuestionKorrigeringAvSynskarpa() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionKorrigeringAvSynskarpa(
      ElementSpecification... children) {
    final var checkboxes = List.of(
        CodeFactory.elementConfigurationCode(
            CodeSystemKorrigeringAvSynskarpa.GLASOGON_INGEN_STYRKA_OVER_8_DIOPTRIER),
        CodeFactory.elementConfigurationCode(
            CodeSystemKorrigeringAvSynskarpa.GLASOGON_MED_STYRKA_OVER_8_DIOPTRIER),
        CodeFactory.elementConfigurationCode(CodeSystemKorrigeringAvSynskarpa.KONTAKTLINSER)
    );

    return ElementSpecification.builder()
        .id(QUESTION_KORRIGERING_AV_SYNSKARPA_ID)
        .configuration(
            ElementConfigurationCheckboxMultipleCode.builder()
                .id(QUESTION_KORRIGERING_AV_SYNSKARPA_FIELD_ID)
                .name("Korrigering av synskärpa genom")
                .elementLayout(ElementLayout.ROWS)
                .list(checkboxes)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.show(
                    QUESTION_SYNSKARPA_ID,
                    new RuleExpression(
                        multipleOrExpression(
                            wrapWithParenthesis(
                                multipleAndExpressions(
                                    lessThanOrEqual(
                                        withCitation(LEFT_EYE_WITH_CORRECTION_ID),
                                        "0.8"
                                    ),
                                    lessThanOrEqual(
                                        withCitation(RIGHT_EYE_WITH_CORRECTION_ID),
                                        "0.1"
                                    )
                                )
                            ),
                            wrapWithParenthesis(
                                multipleAndExpressions(
                                    lessThanOrEqual(
                                        withCitation(LEFT_EYE_WITH_CORRECTION_ID),
                                        "0.1"
                                    ),
                                    lessThanOrEqual(
                                        withCitation(RIGHT_EYE_WITH_CORRECTION_ID),
                                        "0.8"
                                    )
                                )
                            )
                        )
                    )
                ),
                CertificateElementRuleFactory.mandatoryOrExist(
                    QUESTION_KORRIGERING_AV_SYNSKARPA_ID,
                    List.of(
                        new FieldId(
                            CodeSystemKorrigeringAvSynskarpa.GLASOGON_MED_STYRKA_OVER_8_DIOPTRIER.code()),
                        new FieldId(
                            CodeSystemKorrigeringAvSynskarpa.GLASOGON_INGEN_STYRKA_OVER_8_DIOPTRIER.code()),
                        new FieldId(CodeSystemKorrigeringAvSynskarpa.KONTAKTLINSER.code())
                    )
                ),
                CertificateElementRuleFactory.disableSubElements(
                    QUESTION_KORRIGERING_AV_SYNSKARPA_ID,
                    List.of(new FieldId(
                        singleExpression(
                            CodeSystemKorrigeringAvSynskarpa.GLASOGON_MED_STYRKA_OVER_8_DIOPTRIER.code()))),
                    List.of(
                        new FieldId(
                            CodeSystemKorrigeringAvSynskarpa.GLASOGON_INGEN_STYRKA_OVER_8_DIOPTRIER.code())
                    )
                ),
                CertificateElementRuleFactory.disableSubElements(
                    QUESTION_KORRIGERING_AV_SYNSKARPA_ID,
                    List.of(new FieldId(
                        singleExpression(
                            CodeSystemKorrigeringAvSynskarpa.GLASOGON_INGEN_STYRKA_OVER_8_DIOPTRIER.code()))),
                    List.of(
                        new FieldId(
                            CodeSystemKorrigeringAvSynskarpa.GLASOGON_MED_STYRKA_OVER_8_DIOPTRIER.code())
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
        .children(List.of(children))
        .shouldValidate(
            elementData -> elementData.stream()
                .filter(data -> data.id().equals(QUESTION_SYNSKARPA_ID))
                .map(data -> (ElementValueVisualAcuities) data.value())
                .anyMatch(
                    visualAcuities ->
                        (visualAcuities.rightEye().withCorrection().value() != null
                            && visualAcuities.rightEye().withCorrection().value() <= 0.1
                            && visualAcuities.leftEye().withCorrection().value() != null
                            && visualAcuities.leftEye().withCorrection().value() <= 0.8) ||
                            (visualAcuities.rightEye().withCorrection().value() != null
                                && visualAcuities.rightEye().withCorrection().value() <= 0.8
                                && visualAcuities.leftEye().withCorrection().value() != null
                                && visualAcuities.leftEye().withCorrection().value() <= 0.1)
                )
        )
        .mapping(
            new ElementMapping(
                null,
                null,
                Optional.of(CodeListToBoolean.class)
            )
        )
        .build();
  }
}