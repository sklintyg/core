package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;

public class QuestionTransportstod {

  public static final ElementId QUESTION_TRANSPORTSTOD_ID = new ElementId("34");
  public static final FieldId QUESTION_TRANSPORTSTOD_FIELD_ID = new FieldId("34.1");

  private QuestionTransportstod() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionTransportstod(ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_TRANSPORTSTOD_ID)
        .configuration(
            ElementConfigurationCheckboxBoolean.builder()
                .id(QUESTION_TRANSPORTSTOD_FIELD_ID)
                .label(
                    "Patienten skulle kunna arbeta helt eller delvis vid hjälp med transport till och från arbetsplatsen")
                .selectedText("Ja")
                .unselectedText("Ej angivet")
                .build()
        )
        .validations(
            List.of(
                ElementValidationBoolean.builder()
                    .mandatory(false)
                    .build()
            )
        )
        .children(List.of(children))
        .build();
  }
}

