package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSjukdomEllerSynnedsattning.QUESTION_SJUKDOM_ELLER_SYNNEDSATTNING_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionSjukdomEllerSynnedsattning.QUESTION_SJUKDOM_ELLER_SYNNEDSATTNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionSjukdomEllerSynnedsattningBeskrivningV1.QUESTION_SJUKDOM_ELLER_SYNNEDSATTNING_BESKRIVNING_V1_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionSjukdomEllerSynnedsattningBeskrivningV1.QUESTION_SJUKDOM_ELLER_SYNNEDSATTNING_BESKRIVNING_V1_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;

public class QuestionSjukdomEllerSynnedsattningBeskrivningV2 {

  private QuestionSjukdomEllerSynnedsattningBeskrivningV2() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionSjukdomEllerSynnedsattningBeskrivning() {
    return ElementSpecification.builder()
        .id(QUESTION_SJUKDOM_ELLER_SYNNEDSATTNING_BESKRIVNING_V1_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_SJUKDOM_ELLER_SYNNEDSATTNING_BESKRIVNING_V1_FIELD_ID)
                .name("Ange vilken sjukdom/synnedsättning")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.show(
                    QUESTION_SJUKDOM_ELLER_SYNNEDSATTNING_ID,
                    QUESTION_SJUKDOM_ELLER_SYNNEDSATTNING_FIELD_ID
                ),
                CertificateElementRuleFactory.mandatory(
                    QUESTION_SJUKDOM_ELLER_SYNNEDSATTNING_BESKRIVNING_V1_ID,
                    QUESTION_SJUKDOM_ELLER_SYNNEDSATTNING_BESKRIVNING_V1_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_SJUKDOM_ELLER_SYNNEDSATTNING_BESKRIVNING_V1_ID,
                    (short) 250)
            )
        )
        .shouldValidate(
            ElementDataPredicateFactory.valueBoolean(QUESTION_SJUKDOM_ELLER_SYNNEDSATTNING_ID)
        )
        .mapping(
            new ElementMapping(QUESTION_SJUKDOM_ELLER_SYNNEDSATTNING_ID, null)
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

