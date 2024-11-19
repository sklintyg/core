package se.inera.intyg.certificateservice.domain.certificate.service;

import static se.inera.intyg.certificateservice.domain.certificate.model.RelationType.COMPLEMENT;
import static se.inera.intyg.certificateservice.domain.certificate.model.RelationType.RENEW;
import static se.inera.intyg.certificateservice.domain.certificate.model.RelationType.REPLACE;

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
import se.inera.intyg.certificateservice.domain.common.model.Role;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEvent;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEventType;
import se.inera.intyg.certificateservice.domain.event.service.CertificateEventDomainService;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
import se.inera.intyg.certificateservice.domain.message.service.SetMessagesToHandleDomainService;

@RequiredArgsConstructor
public class SignCertificateWithoutSignatureDomainService {

  private final CertificateRepository certificateRepository;
  private final CertificateEventDomainService certificateEventDomainService;
  private final XmlGenerator xmlGenerator;
  private final SetMessagesToHandleDomainService setMessagesToHandleDomainService;

  public Certificate sign(CertificateId certificateId, Revision revision,
      ActionEvaluation actionEvaluation) {
    final var start = LocalDateTime.now(ZoneId.systemDefault());

    final var certificate = certificateRepository.getById(certificateId);
    if (!certificate.allowTo(CertificateActionType.SIGN, Optional.of(actionEvaluation))) {
      throw new CertificateActionForbidden(
          "Not allowed to sign certificate with id %s".formatted(certificateId),
          certificate.reasonNotAllowed(CertificateActionType.SIGN, Optional.of(actionEvaluation))
      );
    }

    if (!Role.PRIVATE_DOCTOR.equals(actionEvaluation.user().role())) {
      throw new CertificateActionForbidden(
          "Only '%s' is allowed to sign without signature! Cannot sign certificate '%s'!"
              .formatted(Role.PRIVATE_DOCTOR.name(), certificateId.id()),
          certificate.reasonNotAllowed(CertificateActionType.SIGN, Optional.of(actionEvaluation))
      );
    }

    certificate.updateMetadata(actionEvaluation);

    certificate.sign(xmlGenerator, revision, actionEvaluation);

    final var signedCertificate = certificateRepository.save(certificate);

    if (signedCertificate.hasParent(COMPLEMENT, RENEW, REPLACE)) {
      setMessagesToHandleDomainService.handle(
          signedCertificate.parent().certificate().messages().stream()
              .filter(message -> message.type() == MessageType.COMPLEMENT)
              .toList()
      );
    }

    certificateEventDomainService.publish(
        CertificateEvent.builder()
            .type(CertificateEventType.SIGNED)
            .start(start)
            .end(LocalDateTime.now(ZoneId.systemDefault()))
            .certificate(signedCertificate)
            .actionEvaluation(actionEvaluation)
            .build()
    );

    return signedCertificate;
  }
}
