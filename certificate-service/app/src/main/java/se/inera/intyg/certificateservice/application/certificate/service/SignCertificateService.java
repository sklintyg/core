package se.inera.intyg.certificateservice.application.certificate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.dto.SignCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.SignCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.service.validation.SignCertificateRequestValidator;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;


@Service
@RequiredArgsConstructor
public class SignCertificateService {

  private final ActionEvaluationFactory actionEvaluationFactory;
  private final SignCertificateRequestValidator signCertificateRequestValidator;

  public SignCertificateResponse sign(SignCertificateRequest signCertificateRequest,
      String certificateId) {
    signCertificateRequestValidator.validate(signCertificateRequest, certificateId);
    final var actionEvaluation = actionEvaluationFactory.create(
        signCertificateRequest.getUser(),
        signCertificateRequest.getUnit(),
        signCertificateRequest.getCareUnit(),
        signCertificateRequest.getCareProvider()
    );

    return SignCertificateResponse.builder()
        .build();
  }
}
