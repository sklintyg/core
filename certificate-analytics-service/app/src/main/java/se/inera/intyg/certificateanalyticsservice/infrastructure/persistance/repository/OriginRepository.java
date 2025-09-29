package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.OriginEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1.OriginEntityMapperV1;

@Repository
@RequiredArgsConstructor
public class OriginRepository {

  private final OriginEntityRepository originEntityRepository;

  public OriginEntity findOrCreate(String origin) {
    return originEntityRepository.findByOrigin(origin)
        .orElseGet(() -> originEntityRepository.save(OriginEntityMapperV1.map(origin)));
  }
}

