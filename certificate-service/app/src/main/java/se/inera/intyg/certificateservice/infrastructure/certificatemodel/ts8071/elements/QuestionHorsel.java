package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionHorsel {

  public static final ElementId QUESTION_HORSEL_ID = new ElementId("9.1");
  public static final FieldId QUESTION_HORSEL_FIELD_ID = new FieldId("9.1");

  private QuestionHorsel() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionHorsel() {
    return ElementSpecification.builder()
        .id(QUESTION_HORSEL_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_HORSEL_FIELD_ID)
                .selectedText("Ja")
                .unselectedText("Nej")
                .name(
                    "Har personen svårt att uppfatta vanlig samtalsstämma på fyra meters avstånd? Hörapparat får användas.")
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
                    QUESTION_HORSEL_ID,
                    QUESTION_HORSEL_FIELD_ID
                )
            )
        )
        .build();
  }
}
