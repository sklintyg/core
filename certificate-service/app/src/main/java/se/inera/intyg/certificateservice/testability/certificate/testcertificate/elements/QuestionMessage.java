package se.inera.intyg.certificateservice.testability.certificate.testcertificate.elements;

import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationMessage;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMessage;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.MessageLevel;


public class QuestionMessage {

  private QuestionMessage() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification questionMessage() {
    return ElementSpecification.builder()
        .id(new ElementId("Test"))
        .configuration(
            ElementConfigurationMessage.builder()
                .message(
                    ElementMessage.builder()
                        .content("MESSAGE")
                        .level(MessageLevel.OBSERVE)
                        .build()
                )
                .build()
        )
        .build();
  }
}