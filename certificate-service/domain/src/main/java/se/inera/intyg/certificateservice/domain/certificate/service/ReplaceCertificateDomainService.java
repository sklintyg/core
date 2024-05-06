package se.inera.intyg.certificateservice.domain.certificate.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEvent;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEventType;
import se.inera.intyg.certificateservice.domain.event.service.CertificateEventDomainService;
import se.inera.intyg.certificateservice.domain.user.model.ExternalReference;

@RequiredArgsConstructor
public class ReplaceCertificateDomainService {

  private final CertificateRepository certificateRepository;
  private final CertificateEventDomainService certificateEventDomainService;

  public Certificate replace(CertificateId certificateId, ActionEvaluation actionEvaluation,
      ExternalReference externalReference) {
    final var start = LocalDateTime.now(ZoneId.systemDefault());

    final var certificate = certificateRepository.getById(certificateId);
    if (!certificate.allowTo(CertificateActionType.REPLACE, actionEvaluation)) {
      throw new CertificateActionForbidden(
          "Not allowed to replace certificate for %s".formatted(certificateId),
          certificate.reasonNotAllowed(CertificateActionType.REPLACE, actionEvaluation)
      );
    }

    final var newCertificate = certificate.replace(actionEvaluation);
    newCertificate.externalReference(externalReference);

    final var savedCertificate = certificateRepository.save(newCertificate);

    certificateEventDomainService.publish(
        CertificateEvent.builder()
            .type(CertificateEventType.REPLACE)
            .start(start)
            .end(LocalDateTime.now(ZoneId.systemDefault()))
            .certificate(savedCertificate)
            .actionEvaluation(actionEvaluation)
            .build()
    );

    return savedCertificate;
  }
}
