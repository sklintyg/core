package se.inera.intyg.certificateservice.domain.certificate.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.Relation;
import se.inera.intyg.certificateservice.domain.certificate.model.RelationType;
import se.inera.intyg.certificateservice.domain.certificate.model.Revision;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PlaceholderRequest;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateModelRepository;
import se.inera.intyg.certificateservice.domain.common.model.ExternalReference;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEvent;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEventType;
import se.inera.intyg.certificateservice.domain.event.service.CertificateEventDomainService;

@RequiredArgsConstructor
public class RenewExternalCertificateDomainService {

  private final CertificateModelRepository certificateModelRepository;
  private final CertificateRepository certificateRepository;
  private final CertificateEventDomainService certificateEventDomainService;

  public Certificate renew(ActionEvaluation actionEvaluation,
      ExternalReference externalReference, PlaceholderRequest request) {
    final var start = LocalDateTime.now(ZoneId.systemDefault());

    final var certificateModel = certificateModelRepository.getById(
        request.certificateModelId()
    );
    //TODO: Do we need to set child for this certificate?
    //TODO: Move creation of newCertificate to repo
    final var placeholderCertificate = certificateRepository.createAndSave(request);

    final var newCertificate = Certificate.builder()
        .id(new CertificateId(UUID.randomUUID().toString()))
        .created(LocalDateTime.now(ZoneId.systemDefault()))
        .certificateModel(certificateModel)
        .revision(new Revision(0))
        .build();

    newCertificate.updateMetadata(actionEvaluation);
    newCertificate.externalReference(externalReference);
    newCertificate.parent(
        Relation.builder()
            .certificate(placeholderCertificate)
            .type(RelationType.RENEW)
            .created(newCertificate.created())
            .build()
    );

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