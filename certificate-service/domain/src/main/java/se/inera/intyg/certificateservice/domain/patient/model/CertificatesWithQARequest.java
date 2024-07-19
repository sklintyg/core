package se.inera.intyg.certificateservice.domain.patient.model;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;

@Value
@Builder
public class CertificatesWithQARequest {

  LocalDateTime from;
  LocalDateTime to;
  HsaId careProviderId;
  List<HsaId> unitIds;
  PersonId personId;
}
