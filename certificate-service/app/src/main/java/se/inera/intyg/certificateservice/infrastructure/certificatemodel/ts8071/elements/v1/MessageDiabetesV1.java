package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionDiabetesV1.QUESTION_DIABETES_FIELD_V1_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071.elements.v1.QuestionDiabetesV1.QUESTION_DIABETES_V1_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationMessage;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMessage;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.MessageLevel;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;

public class MessageDiabetesV1 {

  private MessageDiabetesV1() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification messageDiabetesV1() {
    return ElementSpecification.builder()
        .id(new ElementId("diabetes"))
        .configuration(
            ElementConfigurationMessage.builder()
                .message(
                    ElementMessage.builder()
                        .content(
                            "Har personen läkemedelsbehandlad diabetes krävs normalt ett särskilt läkarintyg om detta. Om personen redan har ett villkor om att skicka in ett nytt diabetesintyg till Transportstyrelsen i framtiden är det dock inte alltid som ett sådant intyg krävs vid prövningen av ansökan. Diabetesintyg går att skicka in digitalt via Webcert. Intygsblanketten finns också på <LINK:transportstyrelsenLink>.")
                        .level(MessageLevel.OBSERVE)
                        .build()
                )
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.show(QUESTION_DIABETES_V1_ID,
                    QUESTION_DIABETES_FIELD_V1_ID)
            )
        )
        .build();
  }
}
