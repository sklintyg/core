package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements;


import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionMissbruk.QUESTION_MISSBRUK_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionMissbrukVard.QUESTION_MISSBRUK_VARD_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionMissbrukVard.QUESTION_MISSBRUK_VARD_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ShouldValidateFactory;

public class QuestionMissbrukVardBeskrivning {

  public static final ElementId QUESTION_MISSBRUK_VARD_BESKRIVNING_ID = new ElementId(
      "18.7");
  public static final FieldId QUESTION_MISSBRUK_VARD_BESKRIVNING_FIELD_ID = new FieldId(
      "18.7");

  private QuestionMissbrukVardBeskrivning() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionMissbrukVardBeskrivning() {
    return ElementSpecification.builder()
        .id(QUESTION_MISSBRUK_VARD_BESKRIVNING_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_MISSBRUK_VARD_BESKRIVNING_FIELD_ID)
                .name(
                    "Ange vilken form av hjälp eller vård och när det var. Beskriv vilken typ av insats det rör sig om.")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.show(
                    QUESTION_MISSBRUK_VARD_ID,
                    QUESTION_MISSBRUK_VARD_FIELD_ID
                ),
                CertificateElementRuleFactory.mandatory(
                    QUESTION_MISSBRUK_VARD_BESKRIVNING_ID,
                    QUESTION_MISSBRUK_VARD_BESKRIVNING_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_MISSBRUK_VARD_BESKRIVNING_ID,
                    (short) 250)
            )
        )
        .shouldValidate(
            ShouldValidateFactory.radioBoolean(QUESTION_MISSBRUK_VARD_ID)
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
