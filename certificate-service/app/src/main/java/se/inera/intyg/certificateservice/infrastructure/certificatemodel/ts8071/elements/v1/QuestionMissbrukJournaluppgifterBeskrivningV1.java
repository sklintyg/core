package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionMissbrukJournaluppgifter.QUESTION_MISSBRUK_JOURNALUPPGIFTER_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.common.QuestionMissbrukJournaluppgifter.QUESTION_MISSBRUK_JOURNALUPPGIFTER_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionMissbrukV1.QUESTION_MISSBRUK_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextField;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;

public class QuestionMissbrukJournaluppgifterBeskrivningV1 {

  public static final ElementId QUESTION_MISSBRUK_JOURNALUPPGIFTER_BESKRIVNING_ID = new ElementId(
      "18.4");
  public static final FieldId QUESTION_MISSBRUK_JOURNALUPPGIFTER_BESKRIVNING_FIELD_ID = new FieldId(
      "18.4");

  private QuestionMissbrukJournaluppgifterBeskrivningV1() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionMissbrukJournaluppgifterBeskrivningV1() {
    return ElementSpecification.builder()
        .id(QUESTION_MISSBRUK_JOURNALUPPGIFTER_BESKRIVNING_ID)
        .configuration(
            ElementConfigurationTextField.builder()
                .id(QUESTION_MISSBRUK_JOURNALUPPGIFTER_BESKRIVNING_FIELD_ID)
                .name("Ange vilka uppgifter eller tecken och när i tid det gäller")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.show(
                    QUESTION_MISSBRUK_JOURNALUPPGIFTER_ID,
                    QUESTION_MISSBRUK_JOURNALUPPGIFTER_FIELD_ID
                ),
                CertificateElementRuleFactory.mandatory(
                    QUESTION_MISSBRUK_JOURNALUPPGIFTER_BESKRIVNING_ID,
                    QUESTION_MISSBRUK_JOURNALUPPGIFTER_BESKRIVNING_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_MISSBRUK_JOURNALUPPGIFTER_BESKRIVNING_ID,
                    (short) 50)
            )
        )
        .shouldValidate(
            ElementDataPredicateFactory.valueBoolean(QUESTION_MISSBRUK_JOURNALUPPGIFTER_ID)
        )
        .mapping(
            new ElementMapping(QUESTION_MISSBRUK_ID, null)
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
