package se.inera.intyg.certificateservice.application.certificate.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateCandidateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateCandidateResponse;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.application.certificate.service.validation.GetCertificateCandidateRequestValidator;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.common.converter.ResourceLinkConverter;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.service.GetCertificateCandidateDomainService;

@Service
@RequiredArgsConstructor
public class GetCertificateCandidateService {

  private final GetCertificateCandidateRequestValidator validator;
  private final ActionEvaluationFactory actionEvaluationFactory;
  private final GetCertificateCandidateDomainService getCertificateCandidateDomainService;
  private final CertificateConverter certificateConverter;
  private final ResourceLinkConverter resourceLinkConverter;

  public GetCertificateCandidateResponse get(GetCertificateCandidateRequest request,
      String certificateId) {
    validator.validate(request, certificateId);

    final var actionEvaluation = actionEvaluationFactory.create(
        request.getUser(),
        request.getUnit(),
        request.getCareUnit(),
        request.getCareProvider()
    );

    final var certificateCandidate = getCertificateCandidateDomainService.get(
        new CertificateId(certificateId), actionEvaluation
    );

    return certificateCandidate
        .map(candidate ->
            GetCertificateCandidateResponse.builder()
                .certificate(certificateConverter.convert(
                        candidate,
                        candidate.actionsInclude(Optional.of(actionEvaluation)).stream()
                            .map(certificateAction ->
                                resourceLinkConverter.convert(
                                    certificateAction,
                                    Optional.of(candidate),
                                    actionEvaluation
                                )
                            )
                            .toList(),
                        actionEvaluation
                    )
                )
                .build()
        )
        .orElse(GetCertificateCandidateResponse.builder().build());
  }
}
