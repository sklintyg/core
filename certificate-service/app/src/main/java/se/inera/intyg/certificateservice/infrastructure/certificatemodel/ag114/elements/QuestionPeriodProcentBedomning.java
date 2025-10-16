package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationInteger;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationInteger;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionPeriodProcentBedomning {

  public static final ElementId QUESTION_PERIOD_PROCENT_BEDOMNING_ID = new ElementId("7");
  public static final FieldId QUESTION_PERIOD_PROCENT_BEDOMNING_FIELD_ID = new FieldId("7.1");

  private QuestionPeriodProcentBedomning() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionPeriodProcentBedomning() {
    return ElementSpecification.builder()
        .id(QUESTION_PERIOD_PROCENT_BEDOMNING_ID)
        .configuration(
            ElementConfigurationInteger.builder()
                .id(QUESTION_PERIOD_PROCENT_BEDOMNING_FIELD_ID)
                .name("Patientens arbetsförmåga bedöms vara nedsatt med (ange antal procent)")
                .min(1)
                .max(100)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    QUESTION_PERIOD_PROCENT_BEDOMNING_ID,
                    QUESTION_PERIOD_PROCENT_BEDOMNING_FIELD_ID
                )
            )
        )
        .validations(
            List.of(
                ElementValidationInteger.builder()
                    .mandatory(true)
                    .min(1)
                    .max(100)
                    .build()
            )
        )
        .build();
  }
}
