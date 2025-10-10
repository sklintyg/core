package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionSmittbararpenning.QUESTION_SMITTBARARPENNING_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionSmittbararpenning.QUESTION_SMITTBARARPENNING_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.ElementDataPredicateFactory;

public class QuestionAktivitetsbegransningar {

  public static final ElementId QUESTION_AKTIVITETSBEGRANSNING_ID = new ElementId(
      "17");
  public static final FieldId QUESTION_AKTIVITETSBEGRANSNING_FIELD_ID = new FieldId("17.1");

  private QuestionAktivitetsbegransningar() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionAktivitetsbegransningar() {
    return ElementSpecification.builder()
        .id(QUESTION_AKTIVITETSBEGRANSNING_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_AKTIVITETSBEGRANSNING_FIELD_ID)
                .name(
                    "Beskriv vad du bedömer att patienten har svårt att göra på grund av sin sjukdom. Ange exempel på sådana begränsningar relaterade till de arbetsuppgifter eller annan sysselsättning som du bedömer arbetsförmågan i förhållande till. Ange om möjligt svårighetsgrad.")
                .build()
        )
        .rules(List.of(
            CertificateElementRuleFactory.mandatory(
                QUESTION_AKTIVITETSBEGRANSNING_ID,
                QUESTION_AKTIVITETSBEGRANSNING_FIELD_ID
            ),
            CertificateElementRuleFactory.hide(
                QUESTION_SMITTBARARPENNING_ID,
                QUESTION_SMITTBARARPENNING_FIELD_ID
            )
        ))
        .validations(List.of(
            ElementValidationText.builder()
                .mandatory(true)
                .limit(4000)
                .build()
        ))
        .shouldValidate(
            ElementDataPredicateFactory.checkboxBoolean(QUESTION_SMITTBARARPENNING_ID, false))
        .build();
  }

}
