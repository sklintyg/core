package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionHorselhjalpmedel {

  public static final ElementId QUESTION_HORSELHJALPMEDEL_ID = new ElementId("9.2");
  public static final FieldId QUESTION_HORSELHJALPMEDEL_FIELD_ID = new FieldId("9.2");

  private QuestionHorselhjalpmedel() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionHorselhjalpmedel(ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_HORSELHJALPMEDEL_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_HORSELHJALPMEDEL_FIELD_ID)
                .selectedText("Ja")
                .unselectedText("Nej")
                .name("Behöver hörapparat användas?")
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
                    QUESTION_HORSELHJALPMEDEL_ID,
                    QUESTION_HORSELHJALPMEDEL_FIELD_ID
                )
            )
        )
        .children(List.of(children))
        .build();
  }
}