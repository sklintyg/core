package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements.QuestionVardasBarnetInneliggandePaSjukhus.QUESTION_VARDAS_BARNET_INNELIGGANDE_PA_SJUKHUS_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements.QuestionVardasBarnetInneliggandePaSjukhus.QUESTION_VARDAS_BARNET_INNELIGGANDE_PA_SJUKHUS_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDateRange;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDateRange;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionPeriodInneliggandePaSjukhus {

  public static final ElementId QUESTION_PERIOD_INNELIGGANDE_ID = new ElementId("62.2");
  private static final FieldId QUESTION_PERIOD_INNELIGGANDE_FIELD_ID = new FieldId("62.2");

  private QuestionPeriodInneliggandePaSjukhus() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionPeriodInneliggandePaSjukhus() {
    return ElementSpecification.builder()
        .id(QUESTION_PERIOD_INNELIGGANDE_ID)
        .configuration(
            ElementConfigurationDateRange.builder()
                .name("Ange period")
                .labelFrom("Fr.o.m")
                .labelTo("T.o.m")
                .id(QUESTION_PERIOD_INNELIGGANDE_FIELD_ID)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    QUESTION_PERIOD_INNELIGGANDE_ID,
                    QUESTION_PERIOD_INNELIGGANDE_FIELD_ID
                ),
                CertificateElementRuleFactory.show(
                    QUESTION_VARDAS_BARNET_INNELIGGANDE_PA_SJUKHUS_ID,
                    QUESTION_VARDAS_BARNET_INNELIGGANDE_PA_SJUKHUS_FIELD_ID
                )
            )
        )
        .validations(
            List.of(
                ElementValidationDateRange.builder()
                    .mandatory(true)
                    .build()
            )
        )
        .build();
  }
}

