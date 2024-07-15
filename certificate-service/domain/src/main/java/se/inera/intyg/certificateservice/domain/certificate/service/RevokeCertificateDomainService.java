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
import se.inera.intyg.certificateservice.domain.common.model.RevokedInformation;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEvent;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEventType;
import se.inera.intyg.certificateservice.domain.event.service.CertificateEventDomainService;
import se.inera.intyg.certificateservice.domain.message.service.SetMessagesToHandleDomainService;

@RequiredArgsConstructor
public class RevokeCertificateDomainService {

  private final CertificateRepository certificateRepository;
  private final CertificateEventDomainService certificateEventDomainService;
  private final SetMessagesToHandleDomainService setMessagesToHandleDomainService;

  public Certificate revoke(CertificateId certificateId, ActionEvaluation actionEvaluation,
      RevokedInformation revokedInformation) {
    final var start = LocalDateTime.now(ZoneId.systemDefault());

    final var certificate = certificateRepository.getById(certificateId);
    if (!certificate.allowTo(CertificateActionType.REVOKE, Optional.of(actionEvaluation))) {
      throw new CertificateActionForbidden(
          "Not allowed to revoke certificate for %s".formatted(certificateId),
          certificate.reasonNotAllowed(CertificateActionType.REVOKE, Optional.of(actionEvaluation))
      );
    }

    certificate.revoke(actionEvaluation, revokedInformation);

    final var revokedCertificate = certificateRepository.save(certificate);

    setMessagesToHandleDomainService.handle(revokedCertificate.messages());

    certificateEventDomainService.publish(
        CertificateEvent.builder()
            .type(CertificateEventType.REVOKED)
            .start(start)
            .end(LocalDateTime.now(ZoneId.systemDefault()))
            .certificate(revokedCertificate)
            .actionEvaluation(actionEvaluation)
            .build()
    );

    return revokedCertificate;
  }
}
