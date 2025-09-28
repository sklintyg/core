package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper;

import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.RoleEntity;

public class RoleEntityMapper {

  public static RoleEntity map(String role) {
    return RoleEntity.builder()
        .role(role)
        .build();
  }
}
