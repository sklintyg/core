package se.inera.intyg.certificateservice.application.certificate.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.inera.intyg.certificateservice.application.certificate.dto.ReplaceCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.ReplaceCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.application.certificate.service.validation.ReplaceCertificateRequestValidator;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.common.converter.ResourceLinkConverter;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.service.ReplaceCertificateDomainService;
import se.inera.intyg.certificateservice.domain.common.model.ExternalReference;

@Service
@RequiredArgsConstructor
public class ReplaceCertificateService {

  private final ReplaceCertificateRequestValidator replaceCertificateRequestValidator;
  private final ActionEvaluationFactory actionEvaluationFactory;
  private final ReplaceCertificateDomainService replaceCertificateDomainService;
  private final CertificateConverter certificateConverter;
  private final ResourceLinkConverter resourceLinkConverter;

  @Transactional
  public ReplaceCertificateResponse replace(ReplaceCertificateRequest replaceCertificateRequest,
      String certificateId) {
    replaceCertificateRequestValidator.validate(replaceCertificateRequest, certificateId);

    final var actionEvaluation = actionEvaluationFactory.create(
        replaceCertificateRequest.getPatient(),
        replaceCertificateRequest.getUser(),
        replaceCertificateRequest.getUnit(),
        replaceCertificateRequest.getCareUnit(),
        replaceCertificateRequest.getCareProvider()
    );

    final var certificate = replaceCertificateDomainService.replace(
        new CertificateId(certificateId),
        actionEvaluation,
        replaceCertificateRequest.getExternalReference() != null
            ? new ExternalReference(replaceCertificateRequest.getExternalReference())
            : null
    );

    return ReplaceCertificateResponse.builder()
        .certificate(
            certificateConverter.convert(
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
