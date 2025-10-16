package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionMissbruk.QUESTION_MISSBRUK_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionMissbrukJournaluppgifter.QUESTION_MISSBRUK_JOURNALUPPGIFTER_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.QuestionMissbrukJournaluppgifter.QUESTION_MISSBRUK_JOURNALUPPGIFTER_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;

public class QuestionMissbrukProvtagning {

  public static final ElementId QUESTION_MISSBRUK_PROVTAGNING_ID = new ElementId(
      "18.5");
  public static final FieldId QUESTION_MISSBRUK_PROVTAGNING_FIELD_ID = new FieldId(
      "18.5");

  private QuestionMissbrukProvtagning() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionMissbrukProvtagning() {
    return ElementSpecification.builder()
        .id(QUESTION_MISSBRUK_PROVTAGNING_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_MISSBRUK_PROVTAGNING_FIELD_ID)
                .name(
                    "Om provtagning gjorts ska resultatet redovisas nedan. Ange datum för provtagning och resultat.")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.show(
                    QUESTION_MISSBRUK_JOURNALUPPGIFTER_ID,
                    QUESTION_MISSBRUK_JOURNALUPPGIFTER_FIELD_ID
                ),
                CertificateElementRuleFactory.mandatory(
                    QUESTION_MISSBRUK_PROVTAGNING_ID,
                    QUESTION_MISSBRUK_PROVTAGNING_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_MISSBRUK_PROVTAGNING_ID,
                    (short) 250)
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
                    .limit(250)
                    .build()
            )
        )
        .build();
  }
}
