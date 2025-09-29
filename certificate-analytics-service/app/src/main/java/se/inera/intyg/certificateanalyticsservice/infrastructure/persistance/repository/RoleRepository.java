package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.RoleEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1.RoleEntityMapperV1;

@Repository
@RequiredArgsConstructor
public class RoleRepository {

  private final RoleEntityRepository roleEntityRepository;

  public RoleEntity findOrCreate(String role) {
    return roleEntityRepository.findByRole(role)
        .orElseGet(() -> roleEntityRepository.save(RoleEntityMapperV1.map(role)));
  }
}

