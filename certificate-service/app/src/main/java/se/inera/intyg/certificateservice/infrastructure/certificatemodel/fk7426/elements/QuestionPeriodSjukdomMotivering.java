package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements;

import se.inera.intyg.certificateservice.domain.certificatemodel.model.*;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

import java.util.List;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionPeriodSjukdom.QUESTION_PERIOD_SJUKDOM_ID;

public class QuestionPeriodSjukdomMotivering {

  public static final ElementId QUESTION_PERIOD_SJUKDOM_MOTIVERING_ID = new ElementId("61.2");
  private static final FieldId QUESTION_PERIOD_SJUKDOM_MOTIVERING_FIELD_ID = new FieldId("61.2");
  private static final short LIMIT = 4000;

  private QuestionPeriodSjukdomMotivering() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionPeriodSjukdomMotivering() {
    return ElementSpecification.builder()
        .id(QUESTION_PERIOD_SJUKDOM_MOTIVERING_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .name("Motivera bedömningen av perioden som du anser att det finns ett påtagligt hot mot barnets liv")
                .id(QUESTION_PERIOD_SJUKDOM_MOTIVERING_FIELD_ID)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(QUESTION_PERIOD_SJUKDOM_MOTIVERING_ID,
                    QUESTION_PERIOD_SJUKDOM_MOTIVERING_FIELD_ID),
                CertificateElementRuleFactory.limit(QUESTION_PERIOD_SJUKDOM_MOTIVERING_ID, LIMIT)
            )
        )
        .validations(
            List.of(
                ElementValidationText.builder()
                    .mandatory(true)
                    .limit(4000)
                    .build()
            )
        ).mapping(new ElementMapping(QUESTION_PERIOD_SJUKDOM_ID, null))
        .build();
  }
}

