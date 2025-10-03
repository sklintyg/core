package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.AdministrativeMessageIdEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.AdministrativeMessageIdEntityMapper;

@Repository
@RequiredArgsConstructor
public class AdministrativeMessageIdRepository {

  private final AdministrativeMessageIdEntityRepository administrativeMessageIdEntityRepository;

  public AdministrativeMessageIdEntity findOrCreate(String administrativeMessageId) {
    if (administrativeMessageId == null || administrativeMessageId.isBlank()) {
      return null;
    }
    return administrativeMessageIdEntityRepository.findByAdministrativeMessageId(
            administrativeMessageId)
        .orElseGet(() -> administrativeMessageIdEntityRepository.save(
            AdministrativeMessageIdEntityMapper.map(administrativeMessageId)));
  }
}
