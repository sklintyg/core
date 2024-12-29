package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionMissbruk {

  public static final ElementId QUESTION_MISSBRUK_ID = new ElementId("18");
  public static final FieldId QUESTION_MISSBRUK_FIELD_ID = new FieldId("18.1");

  private QuestionMissbruk() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionMissbruk(ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_MISSBRUK_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_MISSBRUK_FIELD_ID)
                .name(
                    "Har personen eller har personen haft en diagnos missbruk, beroende eller substansbrukssyndrom?")
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
                    QUESTION_MISSBRUK_ID,
                    QUESTION_MISSBRUK_FIELD_ID
                )
            )
        )
        .children(List.of(children))
        .build();
  }
}