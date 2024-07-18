package se.inera.intyg.certificateservice.application.patient;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.patient.dto.PatientCertificatesWithQARequest;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;
import se.inera.intyg.certificateservice.domain.patient.model.CertificatesWithQARequest;

@Component
public class CertificatesWithQARequestFactory {

  public CertificatesWithQARequest create(
      PatientCertificatesWithQARequest request) {
    return CertificatesWithQARequest.builder()
        .personId(
            PersonId.builder()
                .id(request.getPersonId().getId())
                .type(request.getPersonId().getType().toPersonIdType())
                .build()
        )
        .careProviderId(
            request.getCareProviderId() != null
                ? new HsaId(request.getCareProviderId())
                : null
        )
        .unitIds(
            request.getUnitIds() != null
                ? request.getUnitIds().stream()
                .map(HsaId::new)
                .toList()
                : null
        )
        .build();
  }
}
