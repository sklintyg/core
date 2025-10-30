package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionSynfunktioner {

  public static final ElementId QUESTION_SYNFUNKTIONER_ID = new ElementId("4");
  public static final FieldId QUESTION_SYNFUNKTIONER_FIELD_ID = new FieldId("4.1");

  private QuestionSynfunktioner() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionSynfunktioner() {
    return ElementSpecification.builder()
        .id(QUESTION_SYNFUNKTIONER_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_SYNFUNKTIONER_FIELD_ID)
                .selectedText("Ja")
                .unselectedText("Nej")
                .name("Intyg om synfunktioner kommer att skickas in av legitimerad optiker")
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
                    QUESTION_SYNFUNKTIONER_ID,
                    QUESTION_SYNFUNKTIONER_FIELD_ID
                )
            )
        )
        .build();
  }
}