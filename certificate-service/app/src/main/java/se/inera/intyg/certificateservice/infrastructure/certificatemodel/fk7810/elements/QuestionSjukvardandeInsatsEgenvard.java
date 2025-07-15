package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionSjukvardandeInsatsHSL.QUESTION_SJUKVARDANDE_INSATS_HSL_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionSjukvardandeInsatsEgenvard {

  public static final ElementId QUESTION_SJUKVARDANDE_INSATS_EGENVARD_ID = new ElementId(
      "70.3");
  public static final FieldId QUESTION_SJUKVARDANDE_INSATS_EGENVARD_FIELD_ID = new FieldId("70.3");

  private QuestionSjukvardandeInsatsEgenvard() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionSjukvardandeInsatsEgenvard(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_SJUKVARDANDE_INSATS_EGENVARD_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_SJUKVARDANDE_INSATS_EGENVARD_FIELD_ID)
                .selectedText("Ja")
                .unselectedText("Nej")
                .name(
                    "Har patienten behov av hjälp som bedöms kunna utföras som egenvård?")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatoryExist(
                    QUESTION_SJUKVARDANDE_INSATS_EGENVARD_ID,
                    QUESTION_SJUKVARDANDE_INSATS_EGENVARD_FIELD_ID)
            )
        )
        .mapping(new ElementMapping(QUESTION_SJUKVARDANDE_INSATS_HSL_ID, null))
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
