package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.CertificateTypeEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1.CertificateTypeEntityMapperV1;

@Repository
@RequiredArgsConstructor
public class CertificateTypeRepository {

  private final CertificateTypeEntityRepository certificateTypeEntityRepository;

  public CertificateTypeEntity findOrCreate(String type, String version) {
    return certificateTypeEntityRepository.findByCertificateTypeAndCertificateTypeVersion(type,
        version).orElseGet(() -> certificateTypeEntityRepository.save(
        CertificateTypeEntityMapperV1.map(type, version)));
  }
}

