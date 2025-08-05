package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionMedicinskaSkalForSvarareAtergang {

  public static final ElementId QUESTION_MEDICINSKA_SKAL_ID = new ElementId("33.2");
  private static final FieldId QUESTION_MEDICINSKA_SKAL_FIELD_ID = new FieldId("33.2");

  private QuestionMedicinskaSkalForSvarareAtergang() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionMedicinskaSkalForSvarareAtergang() {
    return ElementSpecification.builder()
        .id(QUESTION_MEDICINSKA_SKAL_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .name(
                    "Beskriv de medicinska skälen till att möjligheterna till återgång i arbete försämras")
                .id(QUESTION_MEDICINSKA_SKAL_FIELD_ID)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    QUESTION_MEDICINSKA_SKAL_ID,
                    QUESTION_MEDICINSKA_SKAL_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_MEDICINSKA_SKAL_ID,
                    (short) 4000
                ) // TODO: Add show
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

