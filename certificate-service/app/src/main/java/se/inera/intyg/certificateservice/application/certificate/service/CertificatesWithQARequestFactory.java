package se.inera.intyg.certificateservice.application.certificate.service;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificatesInternalWithQARequest;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;

@Component
public class CertificatesWithQARequestFactory {

  public se.inera.intyg.certificateservice.domain.patient.model.CertificatesWithQARequest create(
      CertificatesInternalWithQARequest request) {
    return se.inera.intyg.certificateservice.domain.patient.model.CertificatesWithQARequest.builder()
        .certificateIds(
            request.getCertificateIds().stream()
                .map(CertificateId::new)
                .toList()
        )
        .build();
  }
}
