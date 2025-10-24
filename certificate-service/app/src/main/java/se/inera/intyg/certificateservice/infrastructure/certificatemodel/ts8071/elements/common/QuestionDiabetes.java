package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionDiabetes {

  public static final ElementId QUESTION_DIABETES_ID = new ElementId("12");
  public static final FieldId QUESTION_DIABETES_FIELD_ID = new FieldId("12.1");

  private QuestionDiabetes() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionDiabetes() {
    return ElementSpecification.builder()
        .id(QUESTION_DIABETES_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_DIABETES_FIELD_ID)
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
                    QUESTION_DIABETES_ID,
                    QUESTION_DIABETES_FIELD_ID
                )
            )
        )
        .build();
  }
}