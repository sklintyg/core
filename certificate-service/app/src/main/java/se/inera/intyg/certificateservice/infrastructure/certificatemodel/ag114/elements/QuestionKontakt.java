package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;

public class QuestionKontakt {

  public static final ElementId QUESTION_KONTAKT_ID = new ElementId("9");
  public static final FieldId QUESTION_KONTAKT_FIELD_ID = new FieldId("9.1");

  private QuestionKontakt() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionKontakt(ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_KONTAKT_ID)
        .configuration(
            ElementConfigurationCheckboxBoolean.builder()
                .id(QUESTION_KONTAKT_FIELD_ID)
                .name("Kontakt med arbetsgivaren")
                .label(
                    "Jag önskar att arbetsgivaren kontaktar vårdenheten. Patienten har lämnat samtycke för kontakt mellan arbetsgivare och vårdgivare.")
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
