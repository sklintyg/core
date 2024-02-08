package se.inera.intyg.certificateservice.application.certificate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.dto.UpdateCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.UpdateCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.service.validation.UpdateCertificateRequestValidator;

@Service
@RequiredArgsConstructor
public class UpdateCertificateService {

  private final UpdateCertificateRequestValidator updateCertificateRequestValidator;

  public UpdateCertificateResponse update(UpdateCertificateRequest updateCertificateRequest,
      String certificateId) {
    updateCertificateRequestValidator.validate(updateCertificateRequest, certificateId);
    return null;
  }
}
