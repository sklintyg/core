package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1;

import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.UserEntity;

public class UserEntityMapperV1 {

  public static UserEntity map(String userId) {
    return UserEntity.builder()
        .userId(userId)
        .build();
  }
}
