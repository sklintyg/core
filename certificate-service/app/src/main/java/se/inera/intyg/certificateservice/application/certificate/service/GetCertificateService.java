package se.inera.intyg.certificateservice.application.certificate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateResponse;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.service.GetCertificateDomainService;

@Service
@RequiredArgsConstructor
public class GetCertificateService {

  private final ActionEvaluationFactory actionEvaluationFactory;
  private final GetCertificateRequestValidator getCertificateRequestValidator;
  private final GetCertificateDomainService getCertificateDomainService;
  private final CertificateConverter certificateConverter;

  public GetCertificateResponse get(GetCertificateRequest getCertificateRequest,
      String certificateId) {
    getCertificateRequestValidator.validate(getCertificateRequest, certificateId);
    final var actionEvaluation = actionEvaluationFactory.create(
        getCertificateRequest.getPatient(),
        getCertificateRequest.getUser(),
        getCertificateRequest.getUnit(),
        getCertificateRequest.getCareUnit(),
        getCertificateRequest.getCareProvider()
    );
    return GetCertificateResponse.builder()
        .certificate(
            certificateConverter.convert(
                getCertificateDomainService.get(
                    new CertificateId(certificateId),
                    actionEvaluation
                )
            )
        )
        .build();
  }
}
