package se.inera.intyg.certificateservice.domain.certificate.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.staff.model.Staff;

@Value
@Builder
public class ReadyForSign {

  LocalDateTime readyForSignAt;
  Staff readyForSignBy;
}
