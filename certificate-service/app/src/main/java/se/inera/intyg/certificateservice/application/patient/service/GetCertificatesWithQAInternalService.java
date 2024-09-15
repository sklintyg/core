package se.inera.intyg.certificateservice.application.patient.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificatesWithQAInternalRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificatesWithQAInternalResponse;
import se.inera.intyg.certificateservice.application.patient.service.validator.CertificatesWithQARequestValidator;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.patient.service.GetCertificatesWithQAInternalDomainService;

@Service
@RequiredArgsConstructor
public class GetCertificatesWithQAInternalService {

  private final CertificatesWithQARequestValidator certificatesWithQARequestValidator;
  private final GetCertificatesWithQAInternalDomainService getCertificatesWithQAInternalDomainService;

  public CertificatesWithQAInternalResponse get(CertificatesWithQAInternalRequest request) {
    certificatesWithQARequestValidator.validate(request);

    final var xml = getCertificatesWithQAInternalDomainService.get(
        request.getCertificateIds().stream()
            .map(CertificateId::new)
            .toList()
    );

    return CertificatesWithQAInternalResponse.builder()
        .list(xml.base64())
        .build();
  }
}
