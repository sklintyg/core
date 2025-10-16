package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class QuestionArbetsformaga {

  public static final ElementId QUESTION_ARBETSFORMAGA_ID = new ElementId("5");
  public static final FieldId QUESTION_ARBETSFORMAGA_FIELD_ID = new FieldId("5.1");

  private QuestionArbetsformaga() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionArbetsformaga() {
    return ElementSpecification.builder()
        .id(QUESTION_ARBETSFORMAGA_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_ARBETSFORMAGA_FIELD_ID)
                .name("På vilket sätt medför sjukdomen nedsatt arbetsförmåga?")
                .description(
                    """
                        Uppgift om på vilket sätt arbetsförmågan är nedsatt ska anges oavsett om diagnos har angetts eller inte, detta för att arbetstagaren ska kunna styrka sin rätt till sjuklön och rätt att vara frånvarande från arbetet.
                        
                        Informationen har även betydelse för arbetsgivaren i rehabiliteringsarbetet.
                        
                        Uppgift om på vilket sätt sjukdomen medför nedsatt arbetsförmåga blir särskilt viktig om patienten valt att inte uppge diagnos.""")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    QUESTION_ARBETSFORMAGA_ID,
                    QUESTION_ARBETSFORMAGA_FIELD_ID
                )
            )
        )
        .validations(
            List.of(
                ElementValidationText.builder()
                    .mandatory(true)
                    .build()
            )
        )
        .build();
  }
}
