package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionNeurologiskSjukdomV1.QUESTION_NEUROLOGISK_SJUKDOM_V1_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionNeurologiskSjukdomV1.QUESTION_NEUROLOGISK_SJUKDOM_V1_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextField;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;

public class QuestionNeurologiskSjukdomBeskrivningV1 {

  public static final ElementId QUESTION_NEUROLOGISK_SJUKDOM_BESKRIVNING_V1_ID = new ElementId(
      "13.2");
  public static final FieldId QUESTION_NEUROLOGISK_SJUKDOM_BESKRIVNING_V1_FIELD_ID = new FieldId(
      "13.2");

  private QuestionNeurologiskSjukdomBeskrivningV1() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionNeurologiskSjukdomBeskrivningV1() {
    return ElementSpecification.builder()
        .id(QUESTION_NEUROLOGISK_SJUKDOM_BESKRIVNING_V1_ID)
        .configuration(
            ElementConfigurationTextField.builder()
                .id(QUESTION_NEUROLOGISK_SJUKDOM_BESKRIVNING_V1_FIELD_ID)
                .name("Ange vilken sjukdom och vilka tecken")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.show(
                    QUESTION_NEUROLOGISK_SJUKDOM_V1_ID,
                    QUESTION_NEUROLOGISK_SJUKDOM_V1_FIELD_ID
                ),
                CertificateElementRuleFactory.mandatory(
                    QUESTION_NEUROLOGISK_SJUKDOM_BESKRIVNING_V1_ID,
                    QUESTION_NEUROLOGISK_SJUKDOM_BESKRIVNING_V1_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_NEUROLOGISK_SJUKDOM_BESKRIVNING_V1_ID,
                    (short) 50)
            )
        )
        .shouldValidate(
            ElementDataPredicateFactory.valueBoolean(QUESTION_NEUROLOGISK_SJUKDOM_V1_ID)
        )
        .mapping(
            new ElementMapping(QUESTION_NEUROLOGISK_SJUKDOM_V1_ID, null)
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
