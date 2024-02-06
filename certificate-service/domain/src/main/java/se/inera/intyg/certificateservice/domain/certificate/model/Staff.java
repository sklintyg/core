package se.inera.intyg.certificateservice.domain.certificate.model;

import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.patient.model.Name;
import se.inera.intyg.certificateservice.domain.user.model.Role;
import se.inera.intyg.certificateservice.domain.user.model.User;

@Value
@Builder
public class Staff {

  HsaId hsaId;
  Name name;
  Role role;
  Blocked blocked;

  public static Staff create(User user) {
    return Staff.builder()
        .hsaId(user.hsaId())
        .name(user.name())
        .blocked(user.blocked())
        .build();
  }
}
