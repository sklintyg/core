package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDateRange;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionPeriodVardEllerTillsyn {

  public static final ElementId QUESTION_PERIOD_VARD_ELLER_TILLSYN_ID = new ElementId("62.6");
  private static final FieldId QUESTION_PERIOD_VARD_ELER_TILLSYN_FIELD_ID = new FieldId("62.6");

  private QuestionPeriodVardEllerTillsyn() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionPeriodVardEllerTillsyn() {
    return ElementSpecification.builder()
        .id(QUESTION_PERIOD_VARD_ELLER_TILLSYN_ID)
        .configuration(
            ElementConfigurationDateRange.builder()
                .name("Under vilken period behöver barnet vård eller tillsyn?")
                .labelFrom("Fr.o.m")
                .labelTo("T.o.m")
                .id(QUESTION_PERIOD_VARD_ELER_TILLSYN_FIELD_ID)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(QUESTION_PERIOD_VARD_ELLER_TILLSYN_ID,
                    QUESTION_PERIOD_VARD_ELER_TILLSYN_FIELD_ID)
            )
        )
        .build();
  }
}

