package se.inera.intyg.intygproxyservice.integration.authorization.client.converter;

import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.HsaSystemRole;
import se.riv.infrastructure.directory.authorizationmanagement.v2.HsaSystemRoleType;

@Component
public class HsaSystemRoleTypeConverter {

  public HsaSystemRole convert(HsaSystemRoleType type) {
    if (type == null) {
      return HsaSystemRole.builder().build();
    }

    return HsaSystemRole
        .builder()
        .role(type.getRole())
        .systemId(type.getSystemId())
        .build();
  }
}
