package se.inera.intyg.certificateservice.domain.certificate.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.Revision;
import se.inera.intyg.certificateservice.domain.certificate.model.Signature;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEvent;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEventType;
import se.inera.intyg.certificateservice.domain.event.service.CertificateEventDomainService;

@RequiredArgsConstructor
public class SignCertificateDomainService {

  private final CertificateRepository certificateRepository;
  private final CertificateEventDomainService certificateEventDomainService;
  private final XmlGenerator xmlGenerator;

  public Certificate sign(CertificateId certificateId, Revision revision, Signature signature,
      ActionEvaluation actionEvaluation) {
    final var start = LocalDateTime.now(ZoneId.systemDefault());

    final var certificate = certificateRepository.getById(certificateId);
    if (!certificate.allowTo(CertificateActionType.SIGN, actionEvaluation)) {
      throw new CertificateActionForbidden(
          "Not allowed to sign certificate with id %s".formatted(certificateId)
      );
    }

    certificate.updateMetadata(actionEvaluation);

    final var validationResult = certificate.validate();
    if (validationResult.isInvalid()) {
      throw new IllegalArgumentException(
          "Certificate '%s' cannot be signed as it is not valid".formatted(certificateId.id())
      );
    }

    final var xml = xmlGenerator.generate(certificate, signature);
    certificate.sign(xml, revision, actionEvaluation);

    final var signedCertificate = certificateRepository.save(certificate);

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
