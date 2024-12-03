package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionStroke {

  public static final ElementId QUESTION_STROKE_ID = new ElementId("11.5");
  public static final FieldId QUESTION_STROKE_FIELD_ID = new FieldId("11.5");

  private QuestionStroke() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionStroke(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_STROKE_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_STROKE_FIELD_ID)
                .selectedText("Ja")
                .unselectedText("Nej")
                .name(
                    "Har personen haft stroke eller finns tecken på hjärnskada efter trauma, stroke "
                        + "eller annan sjukdom i centrala nervsystemet?")
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
                    QUESTION_STROKE_ID,
                    QUESTION_STROKE_FIELD_ID
                )
            )
        )
        .children(List.of(children))
        .build();
  }
}
