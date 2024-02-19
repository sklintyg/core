package se.inera.intyg.certificateservice.domain.user.model;

import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.patient.model.Name;
import se.inera.intyg.certificateservice.domain.staff.model.Blocked;
import se.inera.intyg.certificateservice.domain.staff.model.Role;

@Value
@Builder
public class User {

  HsaId hsaId;
  Name name;
  Role role;
  Blocked blocked;
}
