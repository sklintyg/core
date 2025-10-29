package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSjukdomEllerSynnedsattning.QUESTION_SJUKDOM_ELLER_SYNNEDSATTNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSjukdomshistorik.QUESTION_SJUKDOMSHISTORIK_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSjukdomshistorik.QUESTION_SJUKDOMSHISTORIK_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;

public class QuestionSjukdomshistorikBeskrivningV2 {

  public static final ElementId QUESTION_SJUKDOMSHISTORIK_BESKRIVNING_V2_ID = new ElementId(
      "7.4");
  public static final FieldId QUESTION_SJUKDOMSHISTORIK_BESKRIVNING_V2_FIELD_ID = new FieldId(
      "7.4");
  private static final int TEXT_LIMIT = 250;

  private QuestionSjukdomshistorikBeskrivningV2() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionSjukdomshistorikBeskrivningV2() {
    return ElementSpecification.builder()
        .id(QUESTION_SJUKDOMSHISTORIK_BESKRIVNING_V2_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_SJUKDOMSHISTORIK_BESKRIVNING_V2_FIELD_ID)
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
                    QUESTION_SJUKDOMSHISTORIK_BESKRIVNING_V2_ID,
                    QUESTION_SJUKDOMSHISTORIK_BESKRIVNING_V2_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_SJUKDOMSHISTORIK_BESKRIVNING_V2_ID, (short) TEXT_LIMIT
                )
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
                    .limit(TEXT_LIMIT)
                    .build()
            )
        )
        .build();
  }
}

