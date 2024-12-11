package se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionNeurologiskSjukdom.QUESTION_NEUROLOGISK_SJUKDOM_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionNeurologiskSjukdom.QUESTION_NEUROLOGISK_SJUKDOM_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ShouldValidateFactory;

public class QuestionNeurologiskSjukdomBeskrivning {

  public static final ElementId QUESTION_NEUROLOGISK_SJUKDOM_BESKRIVNING_ID = new ElementId(
      "13.2");
  public static final FieldId QUESTION_NEUROLOGISK_SJUKDOM_BESKRIVNING_FIELD_ID = new FieldId(
      "13.2");

  private QuestionNeurologiskSjukdomBeskrivning() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionNeurologiskSjukdomBeskrivning() {
    return ElementSpecification.builder()
        .id(QUESTION_NEUROLOGISK_SJUKDOM_BESKRIVNING_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_NEUROLOGISK_SJUKDOM_BESKRIVNING_FIELD_ID)
                .name("Ange vilken sjukdom och vilka tecken")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.show(
                    QUESTION_NEUROLOGISK_SJUKDOM_ID,
                    QUESTION_NEUROLOGISK_SJUKDOM_FIELD_ID
                ),
                CertificateElementRuleFactory.mandatory(
                    QUESTION_NEUROLOGISK_SJUKDOM_BESKRIVNING_ID,
                    QUESTION_NEUROLOGISK_SJUKDOM_BESKRIVNING_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_NEUROLOGISK_SJUKDOM_BESKRIVNING_ID,
                    (short) 250)
            )
        )
        .shouldValidate(
            ShouldValidateFactory.radioBoolean(QUESTION_NEUROLOGISK_SJUKDOM_ID)
        )
        .mapping(
            new ElementMapping(QUESTION_NEUROLOGISK_SJUKDOM_ID, null)
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
