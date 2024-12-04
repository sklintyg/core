package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionSjukdomEllerSynnedsattning.QUESTION_SJUKDOM_ELLER_SYNNEDSATTNING_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionSjukdomEllerSynnedsattning.QUESTION_SJUKDOM_ELLER_SYNNEDSATTNING_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ShouldValidateFactory;

public class QuestionSjukdomEllerSynnedsattningBeskrivning {

  public static final ElementId QUESTION_SJUKDOM_ELLER_SYNNEDSATTNING_BESKRIVNING_ID = new ElementId(
      "7.2");
  public static final FieldId QUESTION_SJUKDOM_ELLER_SYNNEDSATTNING_BESKRIVNING_FIELD_ID = new FieldId(
      "7.2");

  private QuestionSjukdomEllerSynnedsattningBeskrivning() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionSjukdomEllerSynnedsattningBeskrivning() {
    return ElementSpecification.builder()
        .id(QUESTION_SJUKDOM_ELLER_SYNNEDSATTNING_BESKRIVNING_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_SJUKDOM_ELLER_SYNNEDSATTNING_BESKRIVNING_FIELD_ID)
                .name("Ange sjukdom/synnedsättning")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.show(
                    QUESTION_SJUKDOM_ELLER_SYNNEDSATTNING_ID,
                    QUESTION_SJUKDOM_ELLER_SYNNEDSATTNING_FIELD_ID
                ),
                CertificateElementRuleFactory.mandatory(
                    QUESTION_SJUKDOM_ELLER_SYNNEDSATTNING_BESKRIVNING_ID,
                    QUESTION_SJUKDOM_ELLER_SYNNEDSATTNING_BESKRIVNING_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_SJUKDOM_ELLER_SYNNEDSATTNING_BESKRIVNING_ID,
                    (short) 250)
            )
        )
        .shouldValidate(
            ShouldValidateFactory.radioBoolean(QUESTION_SJUKDOM_ELLER_SYNNEDSATTNING_ID)
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
