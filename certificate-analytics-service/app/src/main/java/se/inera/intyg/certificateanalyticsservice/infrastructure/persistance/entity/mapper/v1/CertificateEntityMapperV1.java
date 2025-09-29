package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1;

import se.inera.intyg.certificateanalyticsservice.application.messages.model.v1.CertificateAnalyticsEventCertificateV1;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.CertificateEntity;

public class CertificateEntityMapperV1 {

  public static CertificateEntity map(CertificateAnalyticsEventCertificateV1 certificate) {
    return CertificateEntity.builder()
        .certificateId(certificate.getId())
        .certificateType(CertificateTypeEntityMapperV1.map(certificate))
        .staff(UserEntityMapperV1.map(certificate.getStaffId()))
        .patient(PatientEntityMapperV1.map(certificate.getPatientId()))
        .unit(UnitEntityMapperV1.map(certificate.getUnitId()))
        .careProvider(CareProviderEntityMapperV1.map(certificate.getCareProviderId()))
        .build();
  }
}