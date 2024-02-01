package se.inera.intyg.certificateservice.application.certificate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateResponse;

@Service
@RequiredArgsConstructor
public class GetCertificateService {

  private final GetCertificateRequestValidator getCertificateRequestValidator;

  public GetCertificateResponse get(GetCertificateRequest getCertificateRequest) {
    getCertificateRequestValidator.validate(getCertificateRequest);
    return null;
  }
}
