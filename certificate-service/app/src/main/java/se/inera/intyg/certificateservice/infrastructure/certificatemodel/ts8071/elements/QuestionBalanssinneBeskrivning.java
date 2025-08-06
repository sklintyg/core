package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionBalanssinne.QUESTION_BALANSSINNE_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionBalanssinne.QUESTION_BALANSSINNE_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextField;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ShouldValidateFactory;

public class QuestionBalanssinneBeskrivning {

  public static final ElementId QUESTION_BALANSSINNE_BESKRIVNING_ID = new ElementId(
      "8.2");
  public static final FieldId QUESTION_BALANSSINNE_BESKRIVNING_FIELD_ID = new FieldId(
      "8.2");

  private QuestionBalanssinneBeskrivning() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionBalanssinneBeskrivning() {
    return ElementSpecification.builder()
        .id(QUESTION_BALANSSINNE_BESKRIVNING_ID)
        .configuration(
            ElementConfigurationTextField.builder()
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
                    (short) 50)
            )
        )
        .shouldValidate(
            ShouldValidateFactory.valueBoolean(QUESTION_BALANSSINNE_ID)
        )
        .mapping(
            new ElementMapping(QUESTION_BALANSSINNE_ID, null)
        )
        .validations(
            List.of(
                ElementValidationText.builder()
                    .mandatory(true)
                    .limit(50)
                    .build()
            )
        )
        .build();
  }
}
