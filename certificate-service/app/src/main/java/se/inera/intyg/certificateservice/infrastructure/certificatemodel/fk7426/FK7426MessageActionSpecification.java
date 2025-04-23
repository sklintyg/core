package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426;

import se.inera.intyg.certificateservice.domain.certificatemodel.model.MessageActionSpecification;
import se.inera.intyg.certificateservice.domain.message.model.MessageActionType;

import java.util.List;

public class FK7426MessageActionSpecification {

  private FK7426MessageActionSpecification() {
    throw new IllegalStateException("Utility class");
  }

  public static List<MessageActionSpecification> create() {
    return List.of(
        MessageActionSpecification.builder()
            .messageActionType(MessageActionType.HANDLE_COMPLEMENT)
            .build(),
        MessageActionSpecification.builder()
            .messageActionType(MessageActionType.COMPLEMENT)
            .build(),
        MessageActionSpecification.builder()
            .messageActionType(MessageActionType.CANNOT_COMPLEMENT)
            .build(),
        MessageActionSpecification.builder()
            .messageActionType(MessageActionType.FORWARD)
            .build()
    );
  }
}