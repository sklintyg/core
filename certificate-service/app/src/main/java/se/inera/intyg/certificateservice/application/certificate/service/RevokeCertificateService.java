package se.inera.intyg.certificateservice.application.certificate.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.inera.intyg.certificateservice.application.certificate.dto.RevokeCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.RevokeCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.application.certificate.service.validation.RevokeCertificateRequestValidator;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.common.converter.ResourceLinkConverter;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.service.RevokeCertificateDomainService;
import se.inera.intyg.certificateservice.domain.common.model.RevokedInformation;

@Service
@RequiredArgsConstructor
public class RevokeCertificateService {

  private final ActionEvaluationFactory actionEvaluationFactory;
  private final RevokeCertificateRequestValidator revokeCertificateRequestValidator;
  private final RevokeCertificateDomainService revokeCertificateDomainService;
  private final CertificateConverter certificateConverter;
  private final ResourceLinkConverter resourceLinkConverter;

  @Transactional
  public RevokeCertificateResponse revoke(RevokeCertificateRequest request, String certificateId) {
    revokeCertificateRequestValidator.validate(request, certificateId);

    final var actionEvaluation = actionEvaluationFactory.create(
        request.getUser(),
        request.getUnit(),
        request.getCareUnit(),
        request.getCareProvider()
    );

    final var certificate = revokeCertificateDomainService.revoke(
        new CertificateId(certificateId),
        actionEvaluation,
        new RevokedInformation(request.getRevoked().getReason(), request.getRevoked().getMessage())
    );

    return RevokeCertificateResponse.builder()
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
