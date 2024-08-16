package se.inera.intyg.certificateservice.application.certificate.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.inera.intyg.certificateservice.application.certificate.dto.UpdateCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.UpdateCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.application.certificate.service.converter.ElementCertificateConverter;
import se.inera.intyg.certificateservice.application.certificate.service.validation.UpdateCertificateRequestValidator;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.common.converter.ResourceLinkConverter;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.Revision;
import se.inera.intyg.certificateservice.domain.certificate.service.UpdateCertificateDomainService;
import se.inera.intyg.certificateservice.domain.common.model.ExternalReference;

@Service
@RequiredArgsConstructor
public class UpdateCertificateService {

  private final UpdateCertificateRequestValidator updateCertificateRequestValidator;
  private final UpdateCertificateDomainService updateCertificateDomainService;
  private final ElementCertificateConverter elementCertificateConverter;
  private final ActionEvaluationFactory actionEvaluationFactory;
  private final CertificateConverter certificateConverter;
  private final ResourceLinkConverter resourceLinkConverter;

  @Transactional
  public UpdateCertificateResponse update(UpdateCertificateRequest updateCertificateRequest,
      String certificateId) {
    updateCertificateRequestValidator.validate(updateCertificateRequest, certificateId);

    final var actionEvaluation = actionEvaluationFactory.create(
        updateCertificateRequest.getPatient(),
        updateCertificateRequest.getUser(),
        updateCertificateRequest.getUnit(),
        updateCertificateRequest.getCareUnit(),
        updateCertificateRequest.getCareProvider()
    );

    final var elementData = elementCertificateConverter.convert(
        updateCertificateRequest.getCertificate()
    );

    final var updatedCertificate = updateCertificateDomainService.update(
        new CertificateId(certificateId),
        elementData,
        actionEvaluation,
        new Revision(updateCertificateRequest.getCertificate().getMetadata().getVersion()),
        updateCertificateRequest.getExternalReference() != null
            ? new ExternalReference(updateCertificateRequest.getExternalReference())
            : null
    );

    return UpdateCertificateResponse.builder()
        .certificate(certificateConverter.convert(
                updatedCertificate,
                updatedCertificate.actionsInclude(Optional.of(actionEvaluation)).stream()
                    .map(certificateAction ->
                        resourceLinkConverter.convert(
                            certificateAction,
                            Optional.of(updatedCertificate),
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
