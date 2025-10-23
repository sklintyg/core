package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v2;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionMissbrukV2 {

  public static final ElementId QUESTION_MISSBRUK_V2_ID = new ElementId("18");
  public static final FieldId QUESTION_MISSBRUK_V2_FIELD_ID = new FieldId("18.1");

  private QuestionMissbrukV2() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionMissbrukV2(ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_MISSBRUK_V2_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_MISSBRUK_V2_FIELD_ID)
                .name(
                    "Har personen eller har personen haft en diagnos avseende alkohol, andra psykoaktiva substanser eller läkemedel?")
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
                    QUESTION_MISSBRUK_V2_ID,
                    QUESTION_MISSBRUK_V2_FIELD_ID
                )
            )
        )
        .children(List.of(children))
        .build();
  }
}
