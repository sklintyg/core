package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateanalyticsservice.domain.AdministrativeMessageRelationType;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.AdministrativeMessageRelationTypeEntity;

@Repository
@RequiredArgsConstructor
public class AdministrativeMessageRelationTypeRepository {

  private final AdministrativeMessageRelationTypeEntityRepository administrativeMessageRelationTypeEntityRepository;

  public AdministrativeMessageRelationTypeEntity findOrCreate(
      AdministrativeMessageRelationType relationType) {
    return administrativeMessageRelationTypeEntityRepository.findByRelationType(relationType.name())
        .orElseGet(() -> administrativeMessageRelationTypeEntityRepository.save(
            AdministrativeMessageRelationTypeEntity.builder().relationType(relationType.name())
                .build()
        ));
  }
}
