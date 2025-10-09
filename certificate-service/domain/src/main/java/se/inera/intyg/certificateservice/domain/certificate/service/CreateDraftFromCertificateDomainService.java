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
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateModelRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.common.model.ExternalReference;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEvent;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEventType;
import se.inera.intyg.certificateservice.domain.event.service.CertificateEventDomainService;

@RequiredArgsConstructor
public class CreateDraftFromCertificateDomainService {

  private final CertificateRepository certificateRepository;
  private final CertificateModelRepository certificateModelRepository;
  private final CertificateEventDomainService certificateEventDomainService;

  public Certificate create(CertificateId certificateId, ActionEvaluation actionEvaluation,
      ExternalReference externalReference) {
    final var start = LocalDateTime.now(ZoneId.systemDefault());

    final var certificate = certificateRepository.getById(certificateId);
    if (!certificate.allowTo(CertificateActionType.CREATE_DRAFT_FROM_CERTIFICATE,
        Optional.of(actionEvaluation))) {
      throw new CertificateActionForbidden(
          "Not allowed to create certificate from template for %s".formatted(certificateId),
          certificate.reasonNotAllowed(CertificateActionType.CREATE_DRAFT_FROM_CERTIFICATE,
              Optional.of(actionEvaluation))
      );
    }

    final var certificateModel = certificateModelRepository.getById(
        certificate.certificateModel().getAbleToCreateDraftForModel()
            .orElseThrow(() -> new IllegalStateException(
                "Certificate '%s' is not able to create draft for model".formatted(
                    certificateId.id()))
            )
    );

    final var certificateDraft = certificateRepository.create(certificateModel);

    certificateDraft.fillFromCertificate(certificate);
    certificateDraft.updateMetadata(actionEvaluation);
    certificateDraft.externalReference(externalReference);

    final var savedCertificate = certificateRepository.save(certificateDraft);

    certificateEventDomainService.publish(
        CertificateEvent.builder()
            .type(CertificateEventType.CREATE_DRAFT_FROM_CERTIFICATE)
            .start(start)
            .end(LocalDateTime.now(ZoneId.systemDefault()))
            .certificate(savedCertificate)
            .actionEvaluation(actionEvaluation)
            .build()
    );

    return savedCertificate;
  }
}