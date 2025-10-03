package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.AdministrativeMessageTypeEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.AdministrativeMessageTypeEntityMapper;

@Repository
@RequiredArgsConstructor
public class AdministrativeMessageTypeRepository {

  private final AdministrativeMessageTypeEntityRepository administrativeMessageTypeEntityRepository;

  public AdministrativeMessageTypeEntity findOrCreate(String type) {
    return administrativeMessageTypeEntityRepository.findByType(type)
        .orElseGet(() -> administrativeMessageTypeEntityRepository.save(
            AdministrativeMessageTypeEntityMapper.map(type)));
  }
}
