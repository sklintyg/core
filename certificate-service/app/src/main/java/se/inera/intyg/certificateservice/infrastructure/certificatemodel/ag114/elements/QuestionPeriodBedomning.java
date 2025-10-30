package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionPeriodProcentBedomning.QUESTION_PERIOD_PROCENT_BEDOMNING_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDateRange;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDateRange;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionPeriodBedomning {

  public static final ElementId QUESTION_PERIOD_BEDOMNING_ID = new ElementId("7.2");
  public static final FieldId QUESTION_PERIOD_BEDOMNING_FIELD_ID = new FieldId("7.2");

  private QuestionPeriodBedomning() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionPeriodBedomning() {
    return ElementSpecification.builder()
        .id(QUESTION_PERIOD_BEDOMNING_ID)
        .configuration(
            ElementConfigurationDateRange.builder()
                .id(QUESTION_PERIOD_BEDOMNING_FIELD_ID)
                .name("Period då arbetsförmågan bedöms vara nedsatt")
                .labelFrom("Fr.o.m")
                .labelTo("T.o.m")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    QUESTION_PERIOD_BEDOMNING_ID,
                    QUESTION_PERIOD_BEDOMNING_FIELD_ID
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
        .mapping(
            new ElementMapping(QUESTION_PERIOD_PROCENT_BEDOMNING_ID, null)
        )
        .build();
  }
}
