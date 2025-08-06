package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;

public class QuestionKontakt {

  public static final ElementId QUESTION_KONTAKT_ID = new ElementId(
      "26");
  public static final FieldId QUESTION_KONTAKT_FIELD_ID = new FieldId(
      "26.1");

  private QuestionKontakt() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionKontakt(ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_KONTAKT_ID)
        .configuration(
            ElementConfigurationCheckboxBoolean.builder()
                .id(QUESTION_KONTAKT_FIELD_ID)
                .name("Jag önskar att Försäkringskassan kontaktar mig")
                .build()
        )
        .validations(
            List.of(
                ElementValidationBoolean.builder()
                    .mandatory(true)
                    .build()
            )
        )
        .children(List.of(children))
        .build();
  }

}
