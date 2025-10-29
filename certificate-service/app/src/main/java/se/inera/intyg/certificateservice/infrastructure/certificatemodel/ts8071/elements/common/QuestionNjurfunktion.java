package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionNjurfunktion {

  public static final ElementId QUESTION_NJURFUNKTION_ID = new ElementId("15");
  public static final FieldId QUESTION_NJURFUNKTION_FIELD_ID = new FieldId("15.1");

  private QuestionNjurfunktion() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionNjurfunktion(ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_NJURFUNKTION_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_NJURFUNKTION_FIELD_ID)
                .selectedText("Ja")
                .unselectedText("Nej")
                .name("Har personen allvarligt nedsatt njurfunktion?")
                .description(
                    "Här avses inte tillstånd med bara lätt eller måttligt nedsatt njurfunktion som inte innebär någon trafiksäkerhetsrisk.")
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
                    QUESTION_NJURFUNKTION_ID,
                    QUESTION_NJURFUNKTION_FIELD_ID
                )
            )
        )
        .children(List.of(children))
        .build();
  }
}