package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.PseudonymizedAnalyticsMessage;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.CertificateEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.CareProviderRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.CertificateEntityRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.CertificateTypeRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.PatientRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.UnitRepository;

@Component
@RequiredArgsConstructor
public class CertificateEntityMapper {

  private final CareProviderRepository careProviderRepository;
  private final UnitRepository unitRepository;
  private final PatientRepository patientRepository;
  private final CertificateEntityRepository certificateEntityRepository;
  private final CertificateTypeRepository certificateTypeRepository;

  public CertificateEntity map(PseudonymizedAnalyticsMessage message) {
    return certificateEntityRepository.findByCertificateId(message.getCertificateId())
        .orElseGet(() -> certificateEntityRepository.save(CertificateEntity.builder()
            .certificateId(message.getCertificateId())
            .certificateType(
                certificateTypeRepository.findOrCreate(
                    message.getCertificateType(), message.getCertificateTypeVersion())
            )
            .patient(patientRepository.findOrCreate(message.getCertificatePatientId()))
            .unit(unitRepository.findOrCreate(message.getCertificateUnitId()))
            .careProvider(
                careProviderRepository.findOrCreate(message.getCertificateCareProviderId()))
            .build()
        ));
  }
}