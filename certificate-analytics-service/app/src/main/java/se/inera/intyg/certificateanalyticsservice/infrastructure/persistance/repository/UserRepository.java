package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.UserEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1.UserEntityMapperV1;

@Repository
@RequiredArgsConstructor
public class UserRepository {

  private final UserEntityRepository userEntityRepository;

  public UserEntity findOrCreate(String userId) {
    return userEntityRepository.findByUserId(userId)
        .orElseGet(() -> userEntityRepository.save(UserEntityMapperV1.map(userId)));
  }
}
