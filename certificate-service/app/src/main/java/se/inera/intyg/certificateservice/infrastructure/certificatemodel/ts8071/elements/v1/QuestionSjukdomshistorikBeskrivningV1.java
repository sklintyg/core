package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSjukdomEllerSynnedsattning.QUESTION_SJUKDOM_ELLER_SYNNEDSATTNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSjukdomshistorik.QUESTION_SJUKDOMSHISTORIK_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSjukdomshistorik.QUESTION_SJUKDOMSHISTORIK_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextField;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;

public class QuestionSjukdomshistorikBeskrivningV1 {

  public static final ElementId QUESTION_SJUKDOMSHISTORIK_BESKRIVNING_V1_ID = new ElementId(
      "7.4");
  public static final FieldId QUESTION_SJUKDOMSHISTORIK_BESKRIVNING_FIELD_V1_ID = new FieldId(
      "7.4");

  private QuestionSjukdomshistorikBeskrivningV1() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionSjukdomshistorikBeskrivningV1() {
    return ElementSpecification.builder()
        .id(QUESTION_SJUKDOMSHISTORIK_BESKRIVNING_V1_ID)
        .configuration(
            ElementConfigurationTextField.builder()
                .id(QUESTION_SJUKDOMSHISTORIK_BESKRIVNING_FIELD_V1_ID)
                .name("Ange vad")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.show(
                    QUESTION_SJUKDOMSHISTORIK_ID,
                    QUESTION_SJUKDOMSHISTORIK_FIELD_ID
                ),
                CertificateElementRuleFactory.mandatory(
                    QUESTION_SJUKDOMSHISTORIK_BESKRIVNING_V1_ID,
                    QUESTION_SJUKDOMSHISTORIK_BESKRIVNING_FIELD_V1_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_SJUKDOMSHISTORIK_BESKRIVNING_V1_ID,
                    (short) 50)
            )
        )
        .shouldValidate(
            ElementDataPredicateFactory.valueBoolean(QUESTION_SJUKDOMSHISTORIK_ID)
        )
        .mapping(
            new ElementMapping(QUESTION_SJUKDOM_ELLER_SYNNEDSATTNING_ID, null)
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
