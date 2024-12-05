package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionPsykiskUtvecklingsstorning {

  public static final ElementId QUESTION_PSYKISK_UTVECKLINGSSTORNING_ID = new ElementId("20.6");
  public static final FieldId QUESTION_PSYKISK_UTVECKLINGSSTORNING_FIELD_ID = new FieldId("20.6");

  private QuestionPsykiskUtvecklingsstorning() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionPsykiskUtvecklingsstorning(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_PSYKISK_UTVECKLINGSSTORNING_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_PSYKISK_UTVECKLINGSSTORNING_FIELD_ID)
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
                    QUESTION_PSYKISK_UTVECKLINGSSTORNING_ID,
                    QUESTION_PSYKISK_UTVECKLINGSSTORNING_FIELD_ID
                )
            )
        )
        .children(List.of(children))
        .build();
  }
}
