package se.inera.intyg.certificateservice.application.certificate.service;

import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.dto.ForwardCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.ForwardCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.application.certificate.service.validation.ForwardCertificateRequestValidator;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.common.converter.ResourceLinkConverter;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.certificate.service.ForwardCertificateDomainService;
import se.inera.intyg.certificateservice.domain.certificate.service.ForwardCertificateMessagesDomainService;

@Service
@RequiredArgsConstructor
public class ForwardCertificateService {

  private final ForwardCertificateDomainService forwardCertificateDomainService;
  private final ForwardCertificateMessagesDomainService forwardCertificateMessagesDomainService;
  private final ForwardCertificateRequestValidator forwardCertificateRequestValidator;
  private final CertificateRepository certificateRepository;
  private final ActionEvaluationFactory actionEvaluationFactory;
  private final CertificateConverter certificateConverter;
  private final ResourceLinkConverter resourceLinkConverter;

  @Transactional
  public ForwardCertificateResponse forward(ForwardCertificateRequest request,
      String certificateId) {
    forwardCertificateRequestValidator.validate(request, certificateId);

    final var actionEvaluation = actionEvaluationFactory.create(
        request.getUser(),
        request.getUnit(),
        request.getCareUnit(),
        request.getCareProvider()
    );

    final var certificate = certificateRepository.getById(new CertificateId(certificateId));

    final var forwardedCertificate = certificate.isDraft()
        ? forwardCertificateDomainService.forward(certificate, actionEvaluation)
        : forwardCertificateMessagesDomainService.forward(certificate, actionEvaluation);

    return ForwardCertificateResponse.builder()
        .certificate(certificateConverter.convert(
            forwardedCertificate,
            forwardedCertificate.actionsInclude(Optional.of(actionEvaluation)).stream()
                .map(certificateAction ->
                    resourceLinkConverter.convert(
                        certificateAction,
                        Optional.of(forwardedCertificate),
                        actionEvaluation
                    )
                )
                .toList(),
            actionEvaluation)
        )
        .build();
  }
}
