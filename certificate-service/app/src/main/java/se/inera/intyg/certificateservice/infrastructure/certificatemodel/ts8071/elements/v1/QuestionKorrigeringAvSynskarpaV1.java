package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1;

import static se.inera.intyg.certificateservice.domain.certificate.model.CustomMapperId.CODE_LIST_TO_BOOLEAN;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory.lessThan;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory.multipleAndExpressions;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory.multipleOrExpression;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory.singleExpression;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory.withCitation;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory.wrapWithNotEmpty;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory.wrapWithParenthesis;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSynskarpa.LEFT_EYE_WITHOUT_CORRECTION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSynskarpa.QUESTION_SYNSKARPA_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSynskarpa.RIGHT_EYE_WITHOUT_CORRECTION_ID;

import java.util.List;
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

public class QuestionKorrigeringAvSynskarpaV1 {

  public static final ElementId QUESTION_KORRIGERING_AV_SYNSKARPA_V1_ID = new ElementId("6");
  public static final FieldId QUESTION_KORRIGERING_AV_SYNSKARPA_V1_FIELD_ID = new FieldId("6.1");

  private QuestionKorrigeringAvSynskarpaV1() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionKorrigeringAvSynskarpaV1(
      ElementSpecification... children) {
    final var checkboxes = List.of(
        CodeFactory.elementConfigurationCode(
            CodeSystemKorrigeringAvSynskarpa.GLASOGON_INGEN_STYRKA_OVER_8_DIOPTRIER),
        CodeFactory.elementConfigurationCode(
            CodeSystemKorrigeringAvSynskarpa.GLASOGON_MED_STYRKA_OVER_8_DIOPTRIER),
        CodeFactory.elementConfigurationCode(CodeSystemKorrigeringAvSynskarpa.KONTAKTLINSER)
    );

    return ElementSpecification.builder()
        .id(QUESTION_KORRIGERING_AV_SYNSKARPA_V1_ID)
        .configuration(
            ElementConfigurationCheckboxMultipleCode.builder()
                .id(QUESTION_KORRIGERING_AV_SYNSKARPA_V1_FIELD_ID)
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
                                    wrapWithParenthesis(
                                        multipleAndExpressions(
                                            lessThan(
                                                withCitation(LEFT_EYE_WITHOUT_CORRECTION_ID),
                                                "0.8"
                                            ),
                                            lessThan(
                                                withCitation(RIGHT_EYE_WITHOUT_CORRECTION_ID),
                                                "0.8"
                                            )
                                        )
                                    ),
                                    wrapWithParenthesis(
                                        multipleAndExpressions(
                                            wrapWithNotEmpty(
                                                withCitation(LEFT_EYE_WITHOUT_CORRECTION_ID)),
                                            wrapWithNotEmpty(
                                                withCitation(RIGHT_EYE_WITHOUT_CORRECTION_ID))
                                        )
                                    )
                                )
                            ),
                            wrapWithParenthesis(
                                multipleAndExpressions(
                                    multipleAndExpressions(
                                        wrapWithParenthesis(
                                            multipleOrExpression(
                                                lessThan(
                                                    withCitation(LEFT_EYE_WITHOUT_CORRECTION_ID),
                                                    "0.1"
                                                ),
                                                lessThan(
                                                    withCitation(RIGHT_EYE_WITHOUT_CORRECTION_ID),
                                                    "0.1"
                                                )
                                            )
                                        )
                                    )
                                    ,
                                    wrapWithParenthesis(
                                        multipleAndExpressions(
                                            wrapWithNotEmpty(
                                                withCitation(LEFT_EYE_WITHOUT_CORRECTION_ID)),
                                            wrapWithNotEmpty(
                                                withCitation(RIGHT_EYE_WITHOUT_CORRECTION_ID))
                                        )
                                    )
                                )
                            )
                        )
                    )
                ),
                CertificateElementRuleFactory.mandatoryOrExist(
                    QUESTION_KORRIGERING_AV_SYNSKARPA_V1_ID,
                    List.of(
                        new FieldId(
                            CodeSystemKorrigeringAvSynskarpa.GLASOGON_MED_STYRKA_OVER_8_DIOPTRIER.code()),
                        new FieldId(
                            CodeSystemKorrigeringAvSynskarpa.GLASOGON_INGEN_STYRKA_OVER_8_DIOPTRIER.code()),
                        new FieldId(CodeSystemKorrigeringAvSynskarpa.KONTAKTLINSER.code())
                    )
                ),
                CertificateElementRuleFactory.disableSubElements(
                    QUESTION_KORRIGERING_AV_SYNSKARPA_V1_ID,
                    List.of(new FieldId(
                        singleExpression(
                            CodeSystemKorrigeringAvSynskarpa.GLASOGON_MED_STYRKA_OVER_8_DIOPTRIER.code()))),
                    List.of(
                        new FieldId(
                            CodeSystemKorrigeringAvSynskarpa.GLASOGON_INGEN_STYRKA_OVER_8_DIOPTRIER.code())
                    )
                ),
                CertificateElementRuleFactory.disableSubElements(
                    QUESTION_KORRIGERING_AV_SYNSKARPA_V1_ID,
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
                        (visualAcuities.rightEye().withoutCorrection().value() != null
                            && visualAcuities.rightEye().withoutCorrection().value() < 0.8
                            && visualAcuities.leftEye().withoutCorrection().value() != null
                            && visualAcuities.leftEye().withoutCorrection().value() < 0.8) ||
                            (
                                (visualAcuities.rightEye().withoutCorrection().value() != null
                                    && visualAcuities.rightEye().withoutCorrection().value() < 0.1)
                                    || (visualAcuities.leftEye().withoutCorrection().value() != null
                                    && visualAcuities.leftEye().withoutCorrection().value() < 0.1)
                            )
                )
        )
        .mapping(new ElementMapping(CODE_LIST_TO_BOOLEAN))
        .build();
  }
}