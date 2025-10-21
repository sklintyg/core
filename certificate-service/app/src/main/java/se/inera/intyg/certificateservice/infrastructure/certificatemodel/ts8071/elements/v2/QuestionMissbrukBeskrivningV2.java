package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionMissbrukV2.QUESTION_MISSBRUK_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionMissbrukV2.QUESTION_MISSBRUK_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;

public class QuestionMissbrukBeskrivningV2 {

  public static final ElementId QUESTION_MISSBRUK_BESKRIVNING_ID = new ElementId(
      "18.2");
  public static final FieldId QUESTION_MISSBRUK_BESKRIVNING_FIELD_ID = new FieldId(
      "18.2");

  private QuestionMissbrukBeskrivningV2() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionMissbrukBeskrivningV2() {
    return ElementSpecification.builder()
        .id(QUESTION_MISSBRUK_BESKRIVNING_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_MISSBRUK_BESKRIVNING_FIELD_ID)
                .name(
                    "Ange diagnos, tidpunkt för när diagnosen ställdes och för vilken/vilka substanser")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.show(
                    QUESTION_MISSBRUK_ID,
                    QUESTION_MISSBRUK_FIELD_ID
                ),
                CertificateElementRuleFactory.mandatory(
                    QUESTION_MISSBRUK_BESKRIVNING_ID,
                    QUESTION_MISSBRUK_BESKRIVNING_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_MISSBRUK_BESKRIVNING_ID,
                    (short) 250)
            )
        )
        .shouldValidate(
            ElementDataPredicateFactory.valueBoolean(QUESTION_MISSBRUK_ID)
        )
        .mapping(
            new ElementMapping(QUESTION_MISSBRUK_ID, null)
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
