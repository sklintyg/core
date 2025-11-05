package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionDiabetesV2 {

  public static final ElementId QUESTION_DIABETES_V2_ID = new ElementId("12");
  public static final FieldId QUESTION_DIABETES_FIELD_V2_ID = new FieldId("12.1");

  private QuestionDiabetesV2() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionDiabetesV2() {
    return ElementSpecification.builder()
        .id(QUESTION_DIABETES_V2_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_DIABETES_FIELD_V2_ID)
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
                    QUESTION_DIABETES_V2_ID,
                    QUESTION_DIABETES_FIELD_V2_ID
                )
            )
        )
        .build();
  }
}

