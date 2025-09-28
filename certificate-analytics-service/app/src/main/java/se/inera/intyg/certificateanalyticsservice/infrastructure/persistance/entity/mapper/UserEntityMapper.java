package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper;

import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.UserEntity;

public class UserEntityMapper {

  public static UserEntity map(String staffId) {
    return UserEntity.builder()
        .staffId(staffId)
        .build();
  }
}
