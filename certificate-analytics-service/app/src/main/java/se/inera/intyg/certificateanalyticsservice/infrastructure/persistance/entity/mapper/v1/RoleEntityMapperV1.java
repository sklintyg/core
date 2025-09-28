package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1;

import se.inera.intyg.certificateanalyticsservice.application.messages.model.common.RoleType;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.RoleEntity;

public class RoleEntityMapperV1 {

  public static RoleEntity map(String role) {
    final var type = RoleType.valueOf(role);

    return RoleEntity.builder()
        .roleKey(type.getKey())
        .role(role)
        .build();
  }
}
