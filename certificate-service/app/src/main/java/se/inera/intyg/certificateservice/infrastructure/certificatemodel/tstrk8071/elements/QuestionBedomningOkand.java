package se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemDecision.NO_DECISION;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionBedomning.QUESTION_BEDOMNING_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ShouldValidateFactory;

public class QuestionBedomningOkand {

  public static final ElementId QUESTION_BEDOMNING_OKAND_ID = new ElementId(
      "23.3");
  private static final FieldId QUESTION_BEDOMNING_OKAND_FIELD_ID = new FieldId(
      "23.3");

  private QuestionBedomningOkand() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionBedomningOkand() {
    return ElementSpecification.builder()
        .id(QUESTION_BEDOMNING_OKAND_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_BEDOMNING_OKAND_FIELD_ID)
                .name("Du kan inte ta ställning till om det finns en risk, ange orsaken till detta")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    QUESTION_BEDOMNING_OKAND_ID,
                    QUESTION_BEDOMNING_OKAND_FIELD_ID
                ),
                CertificateElementRuleFactory.show(
                    QUESTION_BEDOMNING_ID,
                    new RuleExpression(String.format("$%s", NO_DECISION.code()))
                )
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
            ShouldValidateFactory.codes(
                QUESTION_BEDOMNING_ID,
                List.of(new FieldId(NO_DECISION.code())))
        )
        .mapping(new ElementMapping(QUESTION_BEDOMNING_ID, NO_DECISION))
        .build();
  }
}
