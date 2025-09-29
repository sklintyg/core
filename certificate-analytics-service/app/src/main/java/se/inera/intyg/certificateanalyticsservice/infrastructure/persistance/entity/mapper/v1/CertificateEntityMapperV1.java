package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.v1.CertificateAnalyticsEventCertificateV1;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.CertificateEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.CareProviderRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.CertificateEntityRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.PatientRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.UnitRepository;

@Component
@RequiredArgsConstructor
public class CertificateEntityMapperV1 {

  private final CareProviderRepository careProviderRepository;
  private final UnitRepository unitRepository;
  private final PatientRepository patientRepository;
  private final CertificateEntityRepository certificateEntityRepository;

  public CertificateEntity map(CertificateAnalyticsEventCertificateV1 certificate) {
    return certificateEntityRepository.findByCertificateId(certificate.getId())
        .orElseGet(() -> CertificateEntity.builder()
            .certificateId(certificate.getId())
            .certificateType(CertificateTypeEntityMapperV1.map(certificate))
            .patient(patientRepository.findOrCreate(certificate.getPatientId()))
            .unit(unitRepository.findOrCreate(certificate.getUnitId()))
            .careProvider(careProviderRepository.findOrCreate(certificate.getCareProviderId()))
            .build());
  }
}