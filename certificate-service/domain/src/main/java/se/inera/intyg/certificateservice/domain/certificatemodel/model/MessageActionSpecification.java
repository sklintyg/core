package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.message.model.MessageActionType;

@Value
@Builder
public class MessageActionSpecification {

  MessageActionType messageActionType;
}