package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.AdministrativeMessageRelationEntity;

@Repository
@RequiredArgsConstructor
public class AdministrativeMessageRelationRepository {

  private final AdministrativeMessageRelationEntityRepository administrativeMessageRelationEntityRepository;

  public AdministrativeMessageRelationEntity findOrCreate(String relationId) {
    return administrativeMessageRelationEntityRepository.findByRelationId(relationId)
        .orElseGet(() -> administrativeMessageRelationEntityRepository.save(
            AdministrativeMessageRelationEntity.builder().relationId(relationId).build()
        ));
  }

  public Optional<AdministrativeMessageRelationEntity> findByRelationId(String relationId) {
    return administrativeMessageRelationEntityRepository.findByRelationId(relationId);
  }
}
