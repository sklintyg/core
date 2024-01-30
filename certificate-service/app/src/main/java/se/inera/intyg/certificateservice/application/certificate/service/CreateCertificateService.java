package se.inera.intyg.certificateservice.application.certificate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateResponse;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;

@Service
@RequiredArgsConstructor
public class CreateCertificateService {

  private final CreateCertificateRequestValidator createCertificateRequestValidator;
  private final ActionEvaluationFactory actionEvaluationFactory;

  public CreateCertificateResponse create(CreateCertificateRequest createCertificateRequest) {
    createCertificateRequestValidator.validate(createCertificateRequest);
    final var actionEvaluation = actionEvaluationFactory.create(
        createCertificateRequest.getPatient(),
        createCertificateRequest.getUser(),
        createCertificateRequest.getUnit(),
        createCertificateRequest.getCareUnit(),
        createCertificateRequest.getCareProvider()
    );
    // Prata med CertificateService
    // Konvertera svaret till vårat DTO-format
    return null;
  }
}
