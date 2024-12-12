package se.inera.intyg.certificateservice.infrastructure.certificatemodel.tstrk8071.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionNeurologiskSjukdom {

  public static final ElementId QUESTION_NEUROLOGISK_SJUKDOM_ID = new ElementId("13");
  public static final FieldId QUESTION_NEUROLOGISK_SJUKDOM_FIELD_ID = new FieldId("13.1");

  private QuestionNeurologiskSjukdom() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionNeurologiskSjukdom(ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_NEUROLOGISK_SJUKDOM_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_NEUROLOGISK_SJUKDOM_FIELD_ID)
                .selectedText("Ja")
                .unselectedText("Nej")
                .name(
                    "Har personen någon neurologisk sjukdom eller finns tecken på neurologisk sjukdom?")
                .description(
                    "Med neurologisk sjukdom avses även medfödda och tidigt förvärvade skador i nervsystemet som lett till begränsad rörelseförmåga och där "
                        + "behov av hjälpmedel för anpassat fordon föreligger.")
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
                    QUESTION_NEUROLOGISK_SJUKDOM_ID,
                    QUESTION_NEUROLOGISK_SJUKDOM_FIELD_ID
                )
            )
        )
        .children(List.of(children))
        .build();
  }
}
