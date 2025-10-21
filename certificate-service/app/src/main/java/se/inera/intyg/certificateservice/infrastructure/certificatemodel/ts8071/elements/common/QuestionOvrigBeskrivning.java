package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionOvrigBeskrivning {

  public static final ElementId QUESTION_OVRIG_BESKRIVNING_ID = new ElementId(
      "22");
  public static final FieldId QUESTION_OVRIG_BESKRIVNING_FIELD_ID = new FieldId(
      "22.1");

  private QuestionOvrigBeskrivning() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionOvrigBeskrivning() {
    return ElementSpecification.builder()
        .id(QUESTION_OVRIG_BESKRIVNING_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_OVRIG_BESKRIVNING_FIELD_ID)
                .name("Ange övriga upplysningar")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.limit(QUESTION_OVRIG_BESKRIVNING_ID, (short) 400)
            )
        )
        .validations(
            List.of(
                ElementValidationText.builder()
                    .limit(400)
                    .build()
            )
        )
        .build();
  }
}
