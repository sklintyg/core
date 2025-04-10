package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionSjukdomEllerSynnedsattning.QUESTION_SJUKDOM_ELLER_SYNNEDSATTNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionSjukdomshistorik.QUESTION_SJUKDOMSHISTORIK_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionSjukdomshistorik.QUESTION_SJUKDOMSHISTORIK_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextField;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ShouldValidateFactory;

public class QuestionSjukdomshistorikBeskrivning {

  public static final ElementId QUESTION_SJUKDOMSHISTORIK_BESKRIVNING_ID = new ElementId(
      "7.4");
  public static final FieldId QUESTION_SJUKDOMSHISTORIK_BESKRIVNING_FIELD_ID = new FieldId(
      "7.4");

  private QuestionSjukdomshistorikBeskrivning() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionSjukdomshistorikBeskrivning() {
    return ElementSpecification.builder()
        .id(QUESTION_SJUKDOMSHISTORIK_BESKRIVNING_ID)
        .configuration(
            ElementConfigurationTextField.builder()
                .id(QUESTION_SJUKDOMSHISTORIK_BESKRIVNING_FIELD_ID)
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
                    QUESTION_SJUKDOMSHISTORIK_BESKRIVNING_ID,
                    QUESTION_SJUKDOMSHISTORIK_BESKRIVNING_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_SJUKDOMSHISTORIK_BESKRIVNING_ID,
                    (short) 50)
            )
        )
        .shouldValidate(
            ShouldValidateFactory.radioBoolean(QUESTION_SJUKDOMSHISTORIK_ID)
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
