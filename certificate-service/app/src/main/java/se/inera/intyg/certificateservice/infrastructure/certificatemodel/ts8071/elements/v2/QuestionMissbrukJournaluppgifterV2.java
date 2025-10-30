package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2.QuestionMissbrukV2.QUESTION_MISSBRUK_V2_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionMissbrukJournaluppgifterV2 {

  public static final ElementId QUESTION_MISSBRUK_JOURNALUPPGIFTER_V2_ID = new ElementId("18.3");
  public static final FieldId QUESTION_MISSBRUK_JOURNALUPPGIFTER_V2_FIELD_ID = new FieldId("18.3");

  private QuestionMissbrukJournaluppgifterV2() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionMissbrukJournaluppgifterV2(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_MISSBRUK_JOURNALUPPGIFTER_V2_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_MISSBRUK_JOURNALUPPGIFTER_V2_FIELD_ID)
                .name(
                    "Finns journaluppgifter, anamnestiska uppgifter, resultat av laboratorieprover eller andra tecken på pågående skadligt bruk, missbruk eller beroende av alkohol, narkotika eller läkemedel?")
                .description(
                    "Här avses uppgifter om eller tecken på beroende av psykoaktiv substans oavsett när i tid detta förekommit. Här avses också uppgifter om eller tecken på aktuellt skadligt mönster av bruk, skadligt bruk eller överkonsumtion av alkohol som inte är tillfälligt under de senaste tolv månaderna.")
                .selectedText("Ja")
                .unselectedText("Nej")
                .build()
        )
        .validations(
            List.of(
                ElementValidationBoolean.builder()
                    .mandatory(true)
                    .build()
            )
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatoryExist(
                    QUESTION_MISSBRUK_JOURNALUPPGIFTER_V2_ID,
                    QUESTION_MISSBRUK_JOURNALUPPGIFTER_V2_FIELD_ID
                )
            )
        )
        .mapping(new ElementMapping(QUESTION_MISSBRUK_V2_ID, null))
        .children(List.of(children))
        .build();
  }
}

