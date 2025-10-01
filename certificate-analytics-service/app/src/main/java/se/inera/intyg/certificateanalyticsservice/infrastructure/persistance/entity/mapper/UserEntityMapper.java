package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper;

import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.UserEntity;

public class UserEntityMapper {

  public static UserEntity map(String userId) {
    return UserEntity.builder()
        .userId(userId)
        .build();
  }
}
