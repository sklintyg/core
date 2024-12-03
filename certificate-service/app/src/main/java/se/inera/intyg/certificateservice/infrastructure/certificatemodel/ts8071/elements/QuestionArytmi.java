package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionArytmi {

  public static final ElementId QUESTION_ARYTMI_ID = new ElementId("11.3");
  public static final FieldId QUESTION_ARYTMI_FIELD_ID = new FieldId("11.3");

  private QuestionArytmi() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionArytmi(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_ARYTMI_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_ARYTMI_FIELD_ID)
                .selectedText("Ja")
                .unselectedText("Nej")
                .name("Har personen eller har personen haft någon arytmi?")
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
                    QUESTION_ARYTMI_ID,
                    QUESTION_ARYTMI_FIELD_ID
                )
            )
        )
        .children(List.of(children))
        .build();
  }
}
