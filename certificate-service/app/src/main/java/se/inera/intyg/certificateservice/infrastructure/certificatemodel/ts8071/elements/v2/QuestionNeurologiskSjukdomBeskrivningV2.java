package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionNeurologiskSjukdomV2.QUESTION_NEUROLOGISK_SJUKDOM_V2_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionNeurologiskSjukdomV2.QUESTION_NEUROLOGISK_SJUKDOM_V2_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;

public class QuestionNeurologiskSjukdomBeskrivningV2 {

  public static final ElementId QUESTION_NEUROLOGISK_SJUKDOM_BESKRIVNING_V2_ID = new ElementId(
      "13.2");
  public static final FieldId QUESTION_NEUROLOGISK_SJUKDOM_BESKRIVNING_V2_FIELD_ID = new FieldId(
      "13.2");
  private static final int TEXT_LIMIT = 250;

  private QuestionNeurologiskSjukdomBeskrivningV2() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionNeurologiskSjukdomBeskrivningV2() {
    return ElementSpecification.builder()
        .id(QUESTION_NEUROLOGISK_SJUKDOM_BESKRIVNING_V2_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_NEUROLOGISK_SJUKDOM_BESKRIVNING_V2_FIELD_ID)
                .name("Ange vilken sjukdom och vilka tecken")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.show(
                    QUESTION_NEUROLOGISK_SJUKDOM_V2_ID,
                    QUESTION_NEUROLOGISK_SJUKDOM_V2_FIELD_ID
                ),
                CertificateElementRuleFactory.mandatory(
                    QUESTION_NEUROLOGISK_SJUKDOM_BESKRIVNING_V2_ID,
                    QUESTION_NEUROLOGISK_SJUKDOM_BESKRIVNING_V2_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_NEUROLOGISK_SJUKDOM_BESKRIVNING_V2_ID, (short) TEXT_LIMIT
                )
            )
        )
        .shouldValidate(
            ElementDataPredicateFactory.valueBoolean(QUESTION_NEUROLOGISK_SJUKDOM_V2_ID)
        )
        .mapping(
            new ElementMapping(QUESTION_NEUROLOGISK_SJUKDOM_V2_ID, null)
        )
        .validations(
            List.of(
                ElementValidationText.builder()
                    .mandatory(true)
                    .limit(TEXT_LIMIT)
                    .build()
            )
        )
        .build();
  }
}