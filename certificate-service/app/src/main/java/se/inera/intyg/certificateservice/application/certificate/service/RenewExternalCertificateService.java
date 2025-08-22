package se.inera.intyg.certificateservice.application.certificate.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.inera.intyg.certificateservice.application.certificate.dto.RenewCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.RenewExternalCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.application.certificate.service.validation.RenewExternalCertificateRequestValidator;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.common.converter.ResourceLinkConverter;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.service.RenewCertificateDomainService;
import se.inera.intyg.certificateservice.domain.common.model.ExternalReference;

@Service
@RequiredArgsConstructor
public class RenewExternalCertificateService {

  private final RenewExternalCertificateRequestValidator renewExternalCertificateRequestValidator;
  private final ActionEvaluationFactory actionEvaluationFactory;
  private final RenewCertificateDomainService renewCertificateDomainService;
  private final CertificateConverter certificateConverter;
  private final ResourceLinkConverter resourceLinkConverter;

  @Transactional
  public RenewCertificateResponse renew(
      RenewExternalCertificateRequest renewExternalCertificateRequest,
      String certificateId) {
    renewExternalCertificateRequestValidator.validate(
        renewExternalCertificateRequest,
        certificateId
    );

    final var actionEvaluation = actionEvaluationFactory.create(
        renewExternalCertificateRequest.getPatient(),
        renewExternalCertificateRequest.getUser(),
        renewExternalCertificateRequest.getUnit(),
        renewExternalCertificateRequest.getCareUnit(),
        renewExternalCertificateRequest.getCareProvider()
    );

    final var certificate = renewCertificateDomainService.renew(
        new CertificateId(certificateId),
        actionEvaluation,
        renewExternalCertificateRequest.getExternalReference() != null
            ? new ExternalReference(renewExternalCertificateRequest.getExternalReference())
            : null
    );

    return RenewCertificateResponse.builder()
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