package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionMissbrukV1.QUESTION_MISSBRUK_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionMissbrukVardV1.QUESTION_MISSBRUK_VARD_V1_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionMissbrukVardV1.QUESTION_MISSBRUK_VARD_V1_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;

public class QuestionMissbrukVardBeskrivningV1 {

  public static final ElementId QUESTION_MISSBRUK_VARD_BESKRIVNING_V1_ID = new ElementId(
      "18.7");
  public static final FieldId QUESTION_MISSBRUK_VARD_BESKRIVNING_V1_FIELD_ID = new FieldId(
      "18.7");

  private QuestionMissbrukVardBeskrivningV1() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionMissbrukVardBeskrivningV1() {
    return ElementSpecification.builder()
        .id(QUESTION_MISSBRUK_VARD_BESKRIVNING_V1_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_MISSBRUK_VARD_BESKRIVNING_V1_FIELD_ID)
                .name(
                    "Ange vilken form av hjälp eller vård och när det var. Beskriv vilken typ av insats det rör sig om.")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.show(
                    QUESTION_MISSBRUK_VARD_V1_ID,
                    QUESTION_MISSBRUK_VARD_V1_FIELD_ID),
                CertificateElementRuleFactory.mandatory(
                    QUESTION_MISSBRUK_VARD_BESKRIVNING_V1_ID,
                    QUESTION_MISSBRUK_VARD_BESKRIVNING_V1_FIELD_ID),
                CertificateElementRuleFactory.limit(
                    QUESTION_MISSBRUK_VARD_BESKRIVNING_V1_ID,
                    (short) 250)
            )
        )
        .shouldValidate(
            ElementDataPredicateFactory.valueBoolean(QUESTION_MISSBRUK_VARD_V1_ID)
        )
        .mapping(
            new ElementMapping(QUESTION_MISSBRUK_V1_ID, null)
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
