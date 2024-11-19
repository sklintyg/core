package se.inera.intyg.certificateservice.application.certificate.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.application.certificate.service.validation.GetCertificateRequestValidator;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.common.converter.ResourceLinkConverter;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.service.GetCertificateDomainService;

@Service
@RequiredArgsConstructor
public class GetCertificateService {

  private final ActionEvaluationFactory actionEvaluationFactory;
  private final GetCertificateRequestValidator getCertificateRequestValidator;
  private final GetCertificateDomainService getCertificateDomainService;
  private final CertificateConverter certificateConverter;
  private final ResourceLinkConverter resourceLinkConverter;

  public GetCertificateResponse get(GetCertificateRequest getCertificateRequest,
      String certificateId) {
    getCertificateRequestValidator.validate(getCertificateRequest, certificateId);
    final var actionEvaluation = actionEvaluationFactory.create(
        getCertificateRequest.getUser(),
        getCertificateRequest.getUnit(),
        getCertificateRequest.getCareUnit(),
        getCertificateRequest.getCareProvider()
    );
    final var certificate = getCertificateDomainService.get(
        new CertificateId(certificateId),
        actionEvaluation
    );

    return GetCertificateResponse.builder()
        .certificate(certificateConverter.convert(
                certificate,
                certificate.actionsInclude(Optional.of(actionEvaluation)).stream()
                    .map(certificateAction ->
                        resourceLinkConverter.convert(
                            certificateAction,
                            Optional.of(certificate),
                            actionEvaluation
                        )
                    )
                    .toList(),
                actionEvaluation
            )
        )
        .build();
  }
}
