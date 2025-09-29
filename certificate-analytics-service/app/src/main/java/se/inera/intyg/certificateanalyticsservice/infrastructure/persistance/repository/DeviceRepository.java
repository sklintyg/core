package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.DeviceEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1.DeviceEntityMapperV1;

@Repository
@RequiredArgsConstructor
public class DeviceRepository {

  private final DeviceEntityRepository deviceEntityRepository;

  public DeviceEntity findOrCreate(String device) {
    return deviceEntityRepository.findByDevice(device)
        .orElseGet(() -> deviceEntityRepository.save(DeviceEntityMapperV1.map()));
  }
}

