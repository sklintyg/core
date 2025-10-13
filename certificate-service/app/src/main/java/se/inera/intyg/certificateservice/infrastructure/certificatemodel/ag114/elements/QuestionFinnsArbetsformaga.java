package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionFinnsArbetsformaga {

  public static final ElementId QUESTION_FINNS_ARBETSFORMAGA_ID = new ElementId("6");
  public static final FieldId QUESTION_FINNS_ARBETSFORMAGA_FIELD_ID = new FieldId("6.1");

  private QuestionFinnsArbetsformaga() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionFinnsArbetsformaga(ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_FINNS_ARBETSFORMAGA_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_FINNS_ARBETSFORMAGA_FIELD_ID)
                .name("Finns arbetsförmåga trots sjukdom?")
                .selectedText("Ja")
                .unselectedText("Nej")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatoryExist(
                    QUESTION_FINNS_ARBETSFORMAGA_ID,
                    QUESTION_FINNS_ARBETSFORMAGA_FIELD_ID
                )
            )
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
