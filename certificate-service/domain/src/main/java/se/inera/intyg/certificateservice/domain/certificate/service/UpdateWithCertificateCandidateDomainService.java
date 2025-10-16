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
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.common.model.ExternalReference;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEvent;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEventType;
import se.inera.intyg.certificateservice.domain.event.service.CertificateEventDomainService;

@RequiredArgsConstructor
public class UpdateWithCertificateCandidateDomainService {

  private final CertificateRepository certificateRepository;
  private final CertificateEventDomainService certificateEventDomainService;

  public Certificate update(CertificateId certificateId,
      CertificateId candidateCertificateId, ActionEvaluation actionEvaluation,
      ExternalReference externalReference) {
    final var start = LocalDateTime.now(ZoneId.systemDefault());

    final var certificate = certificateRepository.getById(certificateId);
    if (!certificate.allowTo(CertificateActionType.UPDATE_DRAFT_FROM_CERTIFICATE,
        Optional.of(actionEvaluation))) {
      throw new CertificateActionForbidden(
          "Not allowed to update with candidate certificate for %s".formatted(certificateId),
          certificate.reasonNotAllowed(CertificateActionType.UPDATE_DRAFT_FROM_CERTIFICATE,
              Optional.of(actionEvaluation))
      );
    }

    final var candidateCertificate = certificateRepository.getById(candidateCertificateId);
    if (!candidateCertificate.allowTo(CertificateActionType.READ, Optional.of(actionEvaluation))) {
      throw new CertificateActionForbidden(
          "Not allowed to read candidate certificate for %s".formatted(certificateId),
          candidateCertificate.reasonNotAllowed(CertificateActionType.READ,
              Optional.of(actionEvaluation))
      );
    }

    certificate.fillFromCertificate(candidateCertificate);
    certificate.updateMetadata(actionEvaluation);

    if (certificate.externalReference() == null) {
      certificate.externalReference(externalReference);
    }

    final var savedCertificate = certificateRepository.save(certificate);

    certificateEventDomainService.publish(
        CertificateEvent.builder()
            .type(CertificateEventType.UPDATE_WITH_CERTIFICATE_CANDIDATE)
            .start(start)
            .end(LocalDateTime.now(ZoneId.systemDefault()))
            .certificate(savedCertificate)
            .actionEvaluation(actionEvaluation)
            .build()
    );

    return savedCertificate;
  }
}
