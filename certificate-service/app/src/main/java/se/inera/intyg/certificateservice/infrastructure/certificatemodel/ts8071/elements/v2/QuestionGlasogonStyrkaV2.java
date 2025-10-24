package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory.lessThan;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory.multipleAndExpressions;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory.multipleOrExpression;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory.withCitation;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory.wrapWithNotEmpty;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory.wrapWithParenthesis;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSynskarpa.LEFT_EYE_WITHOUT_CORRECTION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSynskarpa.QUESTION_SYNSKARPA_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSynskarpa.RIGHT_EYE_WITHOUT_CORRECTION_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;

public class QuestionGlasogonStyrkaV2 {

  public static final ElementId QUESTION_GLASOGON_STYRKA_V2_ID = new ElementId("25");
  public static final FieldId QUESTION_GLASOGON_STYRKA_V2_FIELD_ID = new FieldId("25.1");

  private QuestionGlasogonStyrkaV2() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionGlasogonStyrkaV2() {
    return ElementSpecification.builder()
        .id(QUESTION_GLASOGON_STYRKA_V2_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_GLASOGON_STYRKA_V2_FIELD_ID)
                .name(
                    "Vid korrigering av synskärpa med glasögon, har något av glasen en styrka över plus 8 dioptrier i den mest brytande meridianen?")
                .selectedText("Ja")
                .unselectedText("Nej")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.show(
                    QUESTION_SYNSKARPA_ID,
                    new RuleExpression(
                        multipleAndExpressions(
                            wrapWithParenthesis(
                                multipleOrExpression(
                                    lessThan(
                                        withCitation(RIGHT_EYE_WITHOUT_CORRECTION_ID),
                                        "0.8"
                                    ),
                                    lessThan(
                                        withCitation(LEFT_EYE_WITHOUT_CORRECTION_ID),
                                        "0.8"
                                    )

                                )
                            ),
                            wrapWithParenthesis(
                                multipleAndExpressions(
                                    wrapWithNotEmpty(
                                        withCitation(RIGHT_EYE_WITHOUT_CORRECTION_ID)),
                                    wrapWithNotEmpty(
                                        withCitation(LEFT_EYE_WITHOUT_CORRECTION_ID))
                                )
                            )
                        )
                    )
                ),
                CertificateElementRuleFactory.mandatoryExist(
                    QUESTION_GLASOGON_STYRKA_V2_ID,
                    QUESTION_GLASOGON_STYRKA_V2_FIELD_ID
                )
            )
        )
        .validations(
            List.of(
                ElementValidationBoolean.builder()
                    .mandatory(true)
                    .build()
            )
        )
        .shouldValidate(
            ElementDataPredicateFactory.visualAcuities(QUESTION_SYNSKARPA_ID, 0.8)
        )
        .build();
  }
}

