package se.inera.intyg.certificateanalyticsservice.application.event;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Certificate {

  String id;
  String type;
  String typeVersion;
  String patientId;
  String unitId;
  String careProviderId;
}