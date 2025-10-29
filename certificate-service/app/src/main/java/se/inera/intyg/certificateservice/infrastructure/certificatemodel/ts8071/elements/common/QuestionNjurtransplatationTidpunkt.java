package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionNjurfunktion.QUESTION_NJURFUNKTION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionNjurtransplatation.QUESTION_NJURTRANSPLATATION_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionNjurtransplatation.QUESTION_NJURTRANSPLATATION_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextField;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;

public class QuestionNjurtransplatationTidpunkt {

  public static final ElementId QUESTION_NJURTRANSPLATATION_TIDPUNKT_ID = new ElementId(
      "15.3");
  public static final FieldId QUESTION_NJURTRANSPLATATION_TIDPUNKT_FIELD_ID = new FieldId(
      "15.3");

  private QuestionNjurtransplatationTidpunkt() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionNjurtransplatationTidpunkt() {
    return ElementSpecification.builder()
        .id(QUESTION_NJURTRANSPLATATION_TIDPUNKT_ID)
        .configuration(
            ElementConfigurationTextField.builder()
                .id(QUESTION_NJURTRANSPLATATION_TIDPUNKT_FIELD_ID)
                .name("Ange tidpunkt för transplantationen")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.show(
                    QUESTION_NJURTRANSPLATATION_ID,
                    QUESTION_NJURTRANSPLATATION_FIELD_ID
                ),
                CertificateElementRuleFactory.mandatory(
                    QUESTION_NJURTRANSPLATATION_TIDPUNKT_ID,
                    QUESTION_NJURTRANSPLATATION_TIDPUNKT_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_NJURTRANSPLATATION_TIDPUNKT_ID,
                    (short) 50)
            )
        )
        .shouldValidate(
            ElementDataPredicateFactory.valueBoolean(QUESTION_NJURTRANSPLATATION_ID)
        )
        .mapping(
            new ElementMapping(QUESTION_NJURFUNKTION_ID, null)
        )
        .validations(
            List.of(
                ElementValidationText.builder()
                    .mandatory(true)
                    .limit(50)
                    .build()
            )
        )
        .build();
  }
}
