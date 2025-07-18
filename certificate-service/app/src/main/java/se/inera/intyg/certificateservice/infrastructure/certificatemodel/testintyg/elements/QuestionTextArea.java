package se.inera.intyg.certificateservice.infrastructure.certificatemodel.testintyg.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionTextArea {

  public static final ElementId QUESTION_TEXT_AREA_ID = new ElementId("12");
  public static final FieldId QUESTION_TEXT_AREA_FIELD_ID = new FieldId("12");

  private QuestionTextArea() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionTextArea() {
    return ElementSpecification.builder()
        .id(QUESTION_TEXT_AREA_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_TEXT_AREA_FIELD_ID)
                .name("Test av \"TEXT_AREA\"")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatoryExist(
                    QUESTION_TEXT_AREA_ID,
                    QUESTION_TEXT_AREA_FIELD_ID
                )
            )
        )
        .validations(
            List.of(
                ElementValidationText.builder()
                    .mandatory(true)
                    .build()
            )
        )
        .build();
  }
}
