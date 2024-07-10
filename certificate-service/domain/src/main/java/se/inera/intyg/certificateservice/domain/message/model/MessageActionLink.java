package se.inera.intyg.certificateservice.domain.message.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class MessageActionLink {

  MessageActionType type;
  String name;
  String description;
  boolean enabled;

}
