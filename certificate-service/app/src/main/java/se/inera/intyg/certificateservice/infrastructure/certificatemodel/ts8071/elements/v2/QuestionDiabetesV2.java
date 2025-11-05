package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionDiabetesV1.QUESTION_DIABETES_FIELD_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionDiabetesV1.QUESTION_DIABETES_V1_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionDiabetesV2 {

  private QuestionDiabetesV2() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionDiabetesV2() {
    return ElementSpecification.builder()
        .id(QUESTION_DIABETES_V1_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_DIABETES_FIELD_V1_ID)
                .selectedText("Ja")
                .unselectedText("Nej")
                .name("Har personen läkemedelsbehandlad diabetes?")
                .build()
        )
        .validations(
            List.of(
                ElementValidationBoolean.builder()
                    .mandatory(true)
                    .build()
            )
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatoryExist(
                    QUESTION_DIABETES_V1_ID,
                    QUESTION_DIABETES_FIELD_V1_ID
                )
            )
        )
        .build();
  }
}

