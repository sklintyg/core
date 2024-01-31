package se.inera.intyg.certificateservice.domain.certificate.service;

import se.inera.intyg.certificateservice.domain.certificate.model.Staff;
import se.inera.intyg.certificateservice.domain.user.model.User;

public class IssuerDomainMapper {

  private IssuerDomainMapper() {
    throw new IllegalStateException("Utility class");
  }

  public static Staff map(User user) {
    return Staff.builder()
        .hsaId(user.getHsaId())
        .name(user.getName())
        .blocked(user.getBlocked())
        .build();
  }
}
