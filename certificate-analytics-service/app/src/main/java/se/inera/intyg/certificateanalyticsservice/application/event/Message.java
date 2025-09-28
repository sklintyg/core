package se.inera.intyg.certificateanalyticsservice.application.event;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Message {

  String messageId;
  String type;
  String schemaVersion;
  Certificate certificate;
  Event event;
}

