package se.inera.intyg.certificateservice.application.citizen.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.application.citizen.dto.GetCertificateRequest;
import se.inera.intyg.certificateservice.application.citizen.dto.GetCertificateResponse;
import se.inera.intyg.certificateservice.application.citizen.validation.GetCertificateRequestValidator;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.common.converter.ResourceLinkConverter;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.certificate.service.GetCertificateDomainService;

@Service
@RequiredArgsConstructor
public class GetCertificateService {

  private final ActionEvaluationFactory actionEvaluationFactory;
  private final GetCertificateRequestValidator getCertificateRequestValidator;
  private final GetCertificateDomainService getCertificateDomainService;
  private final CertificateConverter certificateConverter;
  private final ResourceLinkConverter resourceLinkConverter;
  private final CertificateRepository certificateRepository;


  public GetCertificateResponse get(GetCertificateRequest getCertificateRequest,
      String certificateId) {
    final var certificate = certificateRepository.getById(new CertificateId(certificateId));

    return GetCertificateResponse.builder().build();
  }
}
