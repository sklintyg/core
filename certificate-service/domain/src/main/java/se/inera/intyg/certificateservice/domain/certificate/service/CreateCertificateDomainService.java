package se.inera.intyg.certificateservice.domain.certificate.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateModelRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.common.model.ExternalReference;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEvent;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEventType;
import se.inera.intyg.certificateservice.domain.event.service.CertificateEventDomainService;

@RequiredArgsConstructor
public class CreateCertificateDomainService {

  private final CertificateModelRepository certificateModelRepository;
  private final CertificateRepository certificateRepository;
  private final CertificateEventDomainService certificateEventDomainService;

  public Certificate create(CertificateModelId certificateModelId,
      ActionEvaluation actionEvaluation, ExternalReference externalReference) {
    final var start = LocalDateTime.now(ZoneId.systemDefault());

    final var certificateModel = certificateModelRepository.getById(certificateModelId);
    if (!certificateModel.allowTo(CertificateActionType.CREATE, Optional.of(actionEvaluation))) {
      throw new CertificateActionForbidden(
          "Not allowed to create certificate for %s".formatted(certificateModelId),
          certificateModel.reasonNotAllowed(CertificateActionType.CREATE,
              Optional.of(actionEvaluation))
      );
    }

    final var certificate = certificateRepository.create(certificateModel);
    certificate.updateMetadata(actionEvaluation);
    certificate.externalReference(externalReference);

    final var savedCertificate = certificateRepository.save(certificate);

    certificateEventDomainService.publish(
        CertificateEvent.builder()
            .type(CertificateEventType.CREATED)
            .start(start)
            .end(LocalDateTime.now(ZoneId.systemDefault()))
            .certificate(savedCertificate)
            .actionEvaluation(actionEvaluation)
            .build()
    );

    return savedCertificate;
  }
}
