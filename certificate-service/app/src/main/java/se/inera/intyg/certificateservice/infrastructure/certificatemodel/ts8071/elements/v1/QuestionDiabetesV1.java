package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionDiabetesV1 {

  public static final ElementId QUESTION_DIABETES_V1_ID = new ElementId("12");
  public static final FieldId QUESTION_DIABETES_FIELD_V1_ID = new FieldId("12.1");

  private QuestionDiabetesV1() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionDiabetesV1() {
    return ElementSpecification.builder()
        .id(QUESTION_DIABETES_V1_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_DIABETES_FIELD_V1_ID)
                .selectedText("Ja")
                .unselectedText("Nej")
                .name("Har personen läkemedelsbehandlad diabetes?")
                .description(
                    "Har personen läkemedelsbehandlad diabetes krävs ett läkarintyg gällande sjukdomen. Intyget går att skicka in digitalt via Webcert.")
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