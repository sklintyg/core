package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionNjurfunktion.QUESTION_NJURFUNKTION_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionNjurtransplatation {

  public static final ElementId QUESTION_NJURTRANSPLATATION_ID = new ElementId("15.2");
  public static final FieldId QUESTION_NJURTRANSPLATATION_FIELD_ID = new FieldId("15.2");

  private QuestionNjurtransplatation() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionNjurtransplatation(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_NJURTRANSPLATATION_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_NJURTRANSPLATATION_FIELD_ID)
                .selectedText("Ja")
                .unselectedText("Nej")
                .name("Har njurtransplantation genomgåtts?")
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
                    QUESTION_NJURTRANSPLATATION_ID,
                    QUESTION_NJURTRANSPLATATION_FIELD_ID
                )
            )
        )
        .mapping(new ElementMapping(QUESTION_NJURFUNKTION_ID, null))
        .children(List.of(children))
        .build();
  }
}