package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionGrundForBedomning {

  public static final ElementId QUESTION_GRUND_FOR_BEDOMNING_ID = new ElementId(
      "39.2");
  private static final FieldId QUESTION_GRUND_FOR_BEDOMNING_FIELD_ID = new FieldId("39.2");

  private QuestionGrundForBedomning() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionGrundForBedomning() {
    return ElementSpecification.builder()
        .id(QUESTION_GRUND_FOR_BEDOMNING_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_GRUND_FOR_BEDOMNING_FIELD_ID)
                .name(
                    "Beskriv vad som ligger till grund för bedömningen")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.limit(
                    QUESTION_GRUND_FOR_BEDOMNING_ID,
                    (short) 4000)
            )
        )
        .validations(
            List.of(
                ElementValidationText.builder()
                    .mandatory(true)
                    .limit(4000)
                    .build()
            )
        )
        .build();
  }

}
