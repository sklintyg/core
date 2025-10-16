package se.inera.intyg.certificateservice.application.certificate.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.inera.intyg.certificateservice.application.certificate.dto.UpdateWithCertificateCandidateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.UpdateWithCertificateCandidateResponse;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.application.certificate.service.validation.UpdateWithCertificateCandidateRequestValidator;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.common.converter.ResourceLinkConverter;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.service.UpdateWithCertificateCandidateDomainService;
import se.inera.intyg.certificateservice.domain.common.model.ExternalReference;

@Service
@RequiredArgsConstructor
public class UpdateWithCertificateCandidateService {

  private final UpdateWithCertificateCandidateRequestValidator validator;
  private final ActionEvaluationFactory actionEvaluationFactory;
  private final UpdateWithCertificateCandidateDomainService updateWithCertificateCandidateDomainService;
  private final CertificateConverter certificateConverter;
  private final ResourceLinkConverter resourceLinkConverter;

  @Transactional
  public UpdateWithCertificateCandidateResponse update(
      UpdateWithCertificateCandidateRequest request, String certificateId,
      String candidateCertificateId) {

    validator.validate(request, certificateId, candidateCertificateId);

    final var actionEvaluation = actionEvaluationFactory.create(
        request.getPatient(),
        request.getUser(),
        request.getUnit(),
        request.getCareUnit(),
        request.getCareProvider()
    );

    final var certificate = updateWithCertificateCandidateDomainService.update(
        new CertificateId(certificateId),
        new CertificateId(candidateCertificateId),
        actionEvaluation,
        request.getExternalReference() == null ? null
            : new ExternalReference(request.getExternalReference())
    );

    return UpdateWithCertificateCandidateResponse.builder()
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
