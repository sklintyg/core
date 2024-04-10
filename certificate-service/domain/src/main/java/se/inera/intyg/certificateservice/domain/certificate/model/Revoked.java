package se.inera.intyg.certificateservice.domain.certificate.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.common.model.RevokedInformation;
import se.inera.intyg.certificateservice.domain.staff.model.Staff;

@Value
@Builder
public class Revoked {

  RevokedInformation revokedInformation;
  LocalDateTime revokedAt;
  Staff revokedBy;
}
