package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvTs002.YES;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionBedomning.QUESTION_BEDOMNING_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;

public class QuestionBedomningRiskV2 {

  public static final ElementId QUESTION_BEDOMNING_RISK_V2_ID = new ElementId("23.3");
  private static final FieldId QUESTION_BEDOMNING_RISK_V2_FIELD_ID = new FieldId("23.3");

  private QuestionBedomningRiskV2() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionBedomningRiskV2() {
    return ElementSpecification.builder()
        .id(QUESTION_BEDOMNING_RISK_V2_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_BEDOMNING_RISK_V2_FIELD_ID)
                .name("Du bedömer att det finns en risk, ange orsaken till detta")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    QUESTION_BEDOMNING_RISK_V2_ID,
                    QUESTION_BEDOMNING_RISK_V2_FIELD_ID
                ),
                CertificateElementRuleFactory.show(
                    QUESTION_BEDOMNING_ID,
                    new RuleExpression(String.format("$%s", YES.code()))
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_BEDOMNING_RISK_V2_ID,
                    (short) 250)
            )
        )
        .validations(
            List.of(
                ElementValidationText.builder()
                    .mandatory(true)
                    .limit(250)
                    .build()
            )
        )
        .shouldValidate(
            ElementDataPredicateFactory.codes(
                QUESTION_BEDOMNING_ID,
                List.of(new FieldId(YES.code())))
        )
        .mapping(new ElementMapping(QUESTION_BEDOMNING_ID, YES))
        .build();
  }
}

