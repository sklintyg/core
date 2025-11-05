package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionMissbrukVardV2 {

  public static final ElementId QUESTION_MISSBRUK_VARD_V2_ID = new ElementId("18.6");
  public static final FieldId QUESTION_MISSBRUK_VARD_V2_FIELD_ID = new FieldId("18.6");

  private QuestionMissbrukVardV2() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionMissbrukVardV2(ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_MISSBRUK_VARD_V2_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_MISSBRUK_VARD_V2_FIELD_ID)
                .name(
                    "Har personen vid något tillfälle vårdats eller sökt hjälp för substansrelaterad diagnos, överkonsumtion av alkohol eller regelbundet bruk av psykoaktiva substanser eller läkemedel?")
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
                    QUESTION_MISSBRUK_VARD_V2_ID,
                    QUESTION_MISSBRUK_VARD_V2_FIELD_ID
                )
            )
        )
        .mapping(new ElementMapping(QuestionMissbrukV2.QUESTION_MISSBRUK_V2_ID, null))
        .children(List.of(children))
        .build();
  }
}

