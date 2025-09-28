package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper;

import se.inera.intyg.certificateanalyticsservice.application.event.Certificate;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.CertificateEntity;

public class CertificateEntityMapper {

  public static CertificateEntity map(Certificate certificate) {
    final var careProviderEntity = mapCareProvider(certificate.getCareProviderId());
    final var unitEntity = mapUnit(certificate.getUnitId());
    final var patientEntity = mapPatient(certificate.getPatientId());
    return CertificateEntity.builder()
        .certificateId(certificate.getId())
        .certificateType(certificate.getType())
        .certificateTypeVersion(certificate.getTypeVersion())
        .careProvider(careProviderEntity)
        .unit(unitEntity)
        .patient(patientEntity)
        .staffKey(null) // StaffKey not available in Certificate DTO
        .build();
  }
}