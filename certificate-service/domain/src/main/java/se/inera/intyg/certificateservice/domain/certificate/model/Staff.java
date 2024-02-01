package se.inera.intyg.certificateservice.domain.certificate.model;

import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.patient.model.Name;
import se.inera.intyg.certificateservice.domain.user.model.User;

@Value
@Builder
public class Staff {

  HsaId hsaId;
  Name name;
  Blocked blocked;

  public static Staff create(User user) {
    return Staff.builder()
        .hsaId(user.getHsaId())
        .name(user.getName())
        .blocked(user.getBlocked())
        .build();
  }
}
