package se.inera.intyg.certificateanalyticsservice.application.event;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Event {

  LocalDateTime timestamp;
  String messageType;
  String staffId;
  String role;
  String unitId;
  String careProviderId;
  String origin;
  String sessionId;
}

