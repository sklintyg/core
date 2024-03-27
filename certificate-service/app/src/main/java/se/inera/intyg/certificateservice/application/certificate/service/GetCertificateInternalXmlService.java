package se.inera.intyg.certificateservice.application.certificate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateInternalXmlResponse;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;

@Service
@RequiredArgsConstructor
public class GetCertificateInternalXmlService {

  private final CertificateRepository certificateRepository;

  public GetCertificateInternalXmlResponse get(String certificateId) {
    final var certificate = certificateRepository.getById(new CertificateId(certificateId));

    return GetCertificateInternalXmlResponse.builder()
        .certificateId(certificate.id().id())
        .certificateType(certificate.certificateModel().id().type().type())
        .unitId(certificate.certificateMetaData().issuingUnit().hsaId().id())
        .xml(certificate.xml().base64())
        .revoked(certificate.revoked())
        .build();
  }
}
