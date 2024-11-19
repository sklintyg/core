package se.inera.intyg.certificateservice.application.certificate.service;

import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.dto.DeleteCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.DeleteCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.application.certificate.service.validation.DeleteCertificateRequestValidator;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.common.converter.ResourceLinkConverter;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.Revision;
import se.inera.intyg.certificateservice.domain.certificate.service.DeleteCertificateDomainService;

@Service
@RequiredArgsConstructor
public class DeleteCertificateService {

  private final DeleteCertificateRequestValidator deleteCertificateRequestValidator;
  private final ActionEvaluationFactory actionEvaluationFactory;
  private final DeleteCertificateDomainService deleteCertificateDomainService;
  private final CertificateConverter certificateConverter;
  private final ResourceLinkConverter resourceLinkConverter;

  @Transactional
  public DeleteCertificateResponse delete(DeleteCertificateRequest deleteCertificateRequest,
      String certificateId, Long version) {
    deleteCertificateRequestValidator.validate(deleteCertificateRequest, certificateId, version);

    final var actionEvaluation = actionEvaluationFactory.create(
        deleteCertificateRequest.getUser(),
        deleteCertificateRequest.getUnit(),
        deleteCertificateRequest.getCareUnit(),
        deleteCertificateRequest.getCareProvider()
    );

    final var certificate = deleteCertificateDomainService.delete(
        new CertificateId(certificateId),
        new Revision(version),
        actionEvaluation
    );

    return DeleteCertificateResponse.builder()
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
            actionEvaluation)
        )
        .build();
  }
}
