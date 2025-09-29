package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.CareProviderEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1.CareProviderEntityMapperV1;

@Repository
@RequiredArgsConstructor
public class CareProviderRepository {

  private final CareProviderEntityRepository careProviderEntityRepository;

  public CareProviderEntity findOrCreate(String hsaId) {
    return careProviderEntityRepository.findByHsaId(hsaId)
        .orElseGet(() -> careProviderEntityRepository.save(CareProviderEntityMapperV1.map(hsaId)));
  }
}
