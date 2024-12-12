package se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionHjartsjukdom.QUESTION_HJARTSJUKDOM_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements.QuestionHjartsjukdom.QUESTION_HJARTSJUKDOM_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ShouldValidateFactory;

public class QuestionSynkope {

  public static final ElementId QUESTION_SYNKOPE_ID = new ElementId("11.7");
  public static final FieldId QUESTION_SYNKOPE_FIELD_ID = new FieldId("11.7");

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
                ),
                CertificateElementRuleFactory.show(
                    QUESTION_HJARTSJUKDOM_ID,
                    QUESTION_HJARTSJUKDOM_FIELD_ID
                )
            )
        )
        .mapping(new ElementMapping(QUESTION_HJARTSJUKDOM_ID, null))
        .shouldValidate(ShouldValidateFactory.radioBoolean(QUESTION_HJARTSJUKDOM_ID))
        .children(List.of(children))
        .build();
  }
}
