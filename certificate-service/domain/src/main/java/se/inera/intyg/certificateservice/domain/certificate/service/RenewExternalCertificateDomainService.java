package se.inera.intyg.certificateservice.domain.certificate.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateModelRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.common.model.ExternalReference;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEvent;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEventType;
import se.inera.intyg.certificateservice.domain.event.service.CertificateEventDomainService;

@RequiredArgsConstructor
public class RenewExternalCertificateDomainService {

  private final CertificateModelRepository certificateModelRepository;
  private final CertificateRepository certificateRepository;
  private final CertificateEventDomainService certificateEventDomainService;

  public Certificate renew(CertificateId certificateId, ActionEvaluation actionEvaluation,
      ExternalReference externalReference, CertificateModelId certificateModelId) {
    final var start = LocalDateTime.now(ZoneId.systemDefault());
    final var certificateModel = certificateModelRepository.getById(certificateModelId);
    final var certificate = certificateModel.placeholderCertificate(certificateId);
    certificate.updateMetadata(actionEvaluation);

    if (!certificate.allowTo(CertificateActionType.RENEW,
        Optional.of(actionEvaluation))) {
      throw new CertificateActionForbidden(
          "Not allowed to renew certificateModel for %s".formatted(certificateId),
          certificateModel.reasonNotAllowed(CertificateActionType.RENEW,
              Optional.of(actionEvaluation))
      );
    }

    final var placeholderCertificate = certificateRepository.save(certificate);
    final var newCertificate = placeholderCertificate.renew(actionEvaluation);
    newCertificate.externalReference(externalReference);

    final var savedCertificate = certificateRepository.save(newCertificate);

    certificateEventDomainService.publish(
        CertificateEvent.builder()
            .type(CertificateEventType.RENEW)
            .start(start)
            .end(LocalDateTime.now(ZoneId.systemDefault()))
            .certificate(savedCertificate)
            .actionEvaluation(actionEvaluation)
            .build()
    );

    return savedCertificate;
  }
}