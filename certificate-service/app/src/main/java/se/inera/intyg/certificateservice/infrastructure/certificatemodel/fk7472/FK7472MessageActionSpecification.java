package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.MessageActionSpecification;
import se.inera.intyg.certificateservice.domain.message.model.MessageActionType;

public class FK7472MessageActionSpecification {

  private FK7472MessageActionSpecification() {
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
