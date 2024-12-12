package se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionNeuropsykiatrisk {

  public static final ElementId QUESTION_NEUROPSYKIATRISK_ID = new ElementId("20");
  public static final FieldId QUESTION_NEUROPSYKIATRISK_FIELD_ID = new FieldId("20.1");

  private QuestionNeuropsykiatrisk() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionNeuropsykiatrisk(ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_NEUROPSYKIATRISK_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_NEUROPSYKIATRISK_FIELD_ID)
                .name(
                    "Har personen någon neuropsykiatrisk funktionsnedsättning till exempel ADHD, ADD, DCD, Aspergers syndrom eller Tourettes syndrom?")
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
                    QUESTION_NEUROPSYKIATRISK_ID,
                    QUESTION_NEUROPSYKIATRISK_FIELD_ID
                )
            )
        )
        .children(List.of(children))
        .build();
  }
}
