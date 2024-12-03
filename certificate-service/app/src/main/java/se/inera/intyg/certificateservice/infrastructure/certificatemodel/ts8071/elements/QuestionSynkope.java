package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionSynkope {

  public static final ElementId QUESTION_SYNKOPE_ID = new ElementId("11.4");
  public static final FieldId QUESTION_SYNKOPE_FIELD_ID = new FieldId("11.4");

  private QuestionSynkope() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionSynkope(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_SYNKOPE_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_SYNKOPE_FIELD_ID)
                .selectedText("Ja")
                .unselectedText("Nej")
                .name("Har personen eller har personen haft någon synkope?")
                .description(
                    "Med synkope avses här sådan som är utlöst av arytmi men även situationsutlöst synkope (till följd av exempelvis hosta, nysning, skratt eller ansträngning) och reflexsynkope (vasovagal synkope) som exempelvis utlösts av rädsla eller smärta.")
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
                    QUESTION_SYNKOPE_ID,
                    QUESTION_SYNKOPE_FIELD_ID
                )
            )
        )
        .children(List.of(children))
        .build();
  }
}
