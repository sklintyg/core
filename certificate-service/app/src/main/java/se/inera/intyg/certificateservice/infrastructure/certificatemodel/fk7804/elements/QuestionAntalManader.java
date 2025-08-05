package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationInteger;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationInteger;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionAntalManader {

  public static final ElementId QUESTION_ANTAL_MANADER_ID = new ElementId(
      "39.4");
  public static final FieldId QUESTION_ANTAL_MANADER_FIELD_ID = new FieldId(
      "39.4");

  private QuestionAntalManader() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionAntalManader() {
    return ElementSpecification.builder()
        .id(QUESTION_ANTAL_MANADER_ID)
        .configuration(
            ElementConfigurationInteger.builder()
                .id(QUESTION_ANTAL_MANADER_FIELD_ID)
                .name("Ange antal månader")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    QUESTION_ANTAL_MANADER_ID,
                    QUESTION_ANTAL_MANADER_FIELD_ID
                )
            )
        )
        .validations(
            List.of(
                ElementValidationInteger.builder()
                    .mandatory(true)
                    .min(1)
                    .max(99)
                    .build()
            )
        )
        .mapping(
            new ElementMapping(QUESTION_ANTAL_MANADER_ID, null)
        )
        .build();
  }

}
