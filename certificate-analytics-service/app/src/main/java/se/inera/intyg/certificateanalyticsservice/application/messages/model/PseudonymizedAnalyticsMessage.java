package se.inera.intyg.certificateanalyticsservice.application.messages.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PseudonymizedAnalyticsMessage {

  String messageId;

  LocalDateTime eventTimestamp;
  String eventMessageType;
  String eventStaffId;
  String eventRole;
  String eventUnitId;
  String eventCareProviderId;
  String eventOrigin;
  String eventSessionId;

  String certificateId;
  String certificateType;
  String certificateTypeVersion;
  String certificatePatientId;
  String certificateUnitId;
  String certificateCareProviderId;

}