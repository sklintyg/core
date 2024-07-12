package se.inera.intyg.certificateservice.domain.certificate.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.Revision;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEvent;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEventType;
import se.inera.intyg.certificateservice.domain.event.service.CertificateEventDomainService;

@RequiredArgsConstructor
public class DeleteCertificateDomainService {

  private final CertificateRepository certificateRepository;
  private final CertificateEventDomainService certificateEventDomainService;

  public Certificate delete(CertificateId certificateId, Revision revision,
      ActionEvaluation actionEvaluation) {
    final var start = LocalDateTime.now(ZoneId.systemDefault());

    final var certificate = certificateRepository.getById(certificateId);
    if (!certificate.allowTo(CertificateActionType.DELETE, Optional.of(actionEvaluation))) {
      throw new CertificateActionForbidden(
          "Not allowed to delete certificate for %s".formatted(certificateId),
          certificate.reasonNotAllowed(CertificateActionType.DELETE, Optional.of(actionEvaluation))
      );
    }

    certificate.delete(revision, actionEvaluation);
    certificate.updateMetadata(actionEvaluation);

    final var deletedCertificate = certificateRepository.save(certificate);

    certificateEventDomainService.publish(
        CertificateEvent.builder()
            .type(CertificateEventType.DELETED)
            .start(start)
            .end(LocalDateTime.now(ZoneId.systemDefault()))
            .certificate(deletedCertificate)
            .actionEvaluation(actionEvaluation)
            .build()
    );

    return deletedCertificate;
  }
}
