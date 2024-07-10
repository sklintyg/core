package se.inera.intyg.certificateservice.domain.certificate.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.Revision;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.common.model.ExternalReference;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEvent;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEventType;
import se.inera.intyg.certificateservice.domain.event.service.CertificateEventDomainService;

@RequiredArgsConstructor
public class UpdateCertificateDomainService {

  private final CertificateRepository certificateRepository;
  private final CertificateEventDomainService certificateEventDomainService;

  public Certificate update(CertificateId certificateId, List<ElementData> elementData,
      ActionEvaluation actionEvaluation, Revision revision, ExternalReference externalReference) {
    final var start = LocalDateTime.now(ZoneId.systemDefault());

    final var certificate = certificateRepository.getById(certificateId);
    if (!certificate.allowTo(CertificateActionType.UPDATE, Optional.of(actionEvaluation))) {
      throw new CertificateActionForbidden(
          "Not allowed to update certificate for %s".formatted(certificateId),
          certificate.reasonNotAllowed(CertificateActionType.UPDATE, Optional.of(actionEvaluation))
      );
    }

    certificate.updateData(elementData, revision, actionEvaluation);
    certificate.updateMetadata(actionEvaluation);

    if (certificate.externalReference() == null) {
      certificate.externalReference(externalReference);
    }

    final var updatedCertificate = certificateRepository.save(certificate);

    certificateEventDomainService.publish(
        CertificateEvent.builder()
            .type(CertificateEventType.UPDATED)
            .start(start)
            .end(LocalDateTime.now(ZoneId.systemDefault()))
            .certificate(updatedCertificate)
            .actionEvaluation(actionEvaluation)
            .build()
    );

    return updatedCertificate;
  }
}
