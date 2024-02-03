package se.inera.intyg.certificateservice.domain.user.model;

import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.Blocked;
import se.inera.intyg.certificateservice.domain.certificate.model.HsaId;
import se.inera.intyg.certificateservice.domain.patient.model.Name;

@Value
@Builder
public class User {

  HsaId hsaId;
  Name name;
  Role role;
  Blocked blocked;
}
