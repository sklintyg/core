package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionNeuropsykiatriskV1.QUESTION_NEUROPSYKIATRISK_V1_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionPsykiskUtvecklingsstorningV1 {

  public static final ElementId QUESTION_PSYKISK_UTVECKLINGSSTORNING_V1_ID = new ElementId("20.6");
  public static final FieldId QUESTION_PSYKISK_UTVECKLINGSSTORNING_FIELD_V1_ID = new FieldId(
      "20.6");

  private QuestionPsykiskUtvecklingsstorningV1() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionPsykiskUtvecklingsstorningV1(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_PSYKISK_UTVECKLINGSSTORNING_V1_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_PSYKISK_UTVECKLINGSSTORNING_FIELD_V1_ID)
                .name("Har personen någon psykisk utvecklingsstörning?")
                .selectedText("Ja")
                .unselectedText("Nej")
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
                    QUESTION_PSYKISK_UTVECKLINGSSTORNING_V1_ID,
                    QUESTION_PSYKISK_UTVECKLINGSSTORNING_FIELD_V1_ID
                )
            )
        )
        .mapping(new ElementMapping(QUESTION_NEUROPSYKIATRISK_V1_ID, null))
        .children(List.of(children))
        .build();
  }
}