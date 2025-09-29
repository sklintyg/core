package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1;

import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.RoleEntity;

public class RoleEntityMapperV1 {

  public static RoleEntity map(String role) {
    return RoleEntity.builder()
        .role(role)
        .build();
  }
}
