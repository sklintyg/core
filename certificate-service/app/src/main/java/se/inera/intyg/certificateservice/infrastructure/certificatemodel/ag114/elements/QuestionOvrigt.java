package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;

public class QuestionOvrigt {

  public static final ElementId QUESTION_OVRIGT_ID = new ElementId("8");
  public static final FieldId QUESTION_OVRIGT_FIELD_ID = new FieldId("8.1");

  private QuestionOvrigt() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionOvrigt() {
    return ElementSpecification.builder()
        .id(QUESTION_OVRIGT_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_OVRIGT_FIELD_ID)
                .name("Övriga upplysningar till arbetsgivaren")
                .build()
        )
        .validations(
            List.of(
                ElementValidationText.builder()
                    .mandatory(false)
                    .build()
            )
        )
        .build();
  }
}
