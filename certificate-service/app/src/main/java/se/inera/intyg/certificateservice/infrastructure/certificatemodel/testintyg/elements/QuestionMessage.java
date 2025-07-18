package se.inera.intyg.certificateservice.infrastructure.certificatemodel.testintyg.elements;

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
                        .content(
                            "Test av \"MESSAGE\" element. " +
                                "Detta är ett testmeddelande som ska visas i certifikatet.")
                        .level(MessageLevel.OBSERVE)
                        .build()
                )
                .build()
        )
        .build();
  }
}

