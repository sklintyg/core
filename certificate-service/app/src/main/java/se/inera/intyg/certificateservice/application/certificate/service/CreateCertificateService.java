package se.inera.intyg.certificateservice.application.certificate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateResponse;

@Service
@RequiredArgsConstructor
public class CreateCertificateService {

  private final CreateCertificateRequestValidator createCertificateRequestValidator;

  public CreateCertificateResponse create(CreateCertificateRequest createCertificateRequest) {
    createCertificateRequestValidator.validate(createCertificateRequest);
    // Skapa actionEvaluation
    // Prata med CertificateService
    // Konvertera svaret till vårat DTO-format
    return null;
  }
}
