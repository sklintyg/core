package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionBalanssinne.QUESTION_BALANSSINNE_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionBalanssinne.QUESTION_BALANSSINNE_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ShouldValidateFactory;

public class QuestionBalanssinneBeskrivning {

  public static final ElementId QUESTION_BALANSSINNE_BESKRIVNING_ID = new ElementId(
      "8.1.1");
  public static final FieldId QUESTION_BALANSSINNE_BESKRIVNING_FIELD_ID = new FieldId(
      "8.1");

  private QuestionBalanssinneBeskrivning() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionBalanssinneBeskrivning() {
    return ElementSpecification.builder()
        .id(QUESTION_BALANSSINNE_BESKRIVNING_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_BALANSSINNE_BESKRIVNING_FIELD_ID)
                .name("Ange vilken typ av anfall och tidpunkt för senaste anfall")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.show(
                    QUESTION_BALANSSINNE_ID,
                    QUESTION_BALANSSINNE_FIELD_ID
                ),
                CertificateElementRuleFactory.mandatory(
                    QUESTION_BALANSSINNE_BESKRIVNING_ID,
                    QUESTION_BALANSSINNE_BESKRIVNING_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_BALANSSINNE_BESKRIVNING_ID,
                    (short) 250)
            )
        )
        .shouldValidate(
            ShouldValidateFactory.radioBoolean(QUESTION_BALANSSINNE_ID)
        )
        .validations(
            List.of(
                ElementValidationText.builder()
                    .mandatory(true)
                    .limit(250)
                    .build()
            )
        )
        .build();
  }
}
