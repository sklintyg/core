package se.inera.intyg.certificateservice.domain.certificate.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionRuleCertificateTypeActiveForUnit;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateActionConfigurationRepository;
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
  private final CertificateActionConfigurationRepository certificateActionConfigurationRepository;
  private final PrefillProcessor prefillProcessor;

  public Certificate create(CertificateModelId certificateModelId,
      ActionEvaluation actionEvaluation, ExternalReference externalReference,
      Xml prefillXml) {

    final var start = LocalDateTime.now(ZoneId.systemDefault());

    final var certificateModel = certificateModelRepository.getActiveById(certificateModelId);
    if (!certificateModel.allowTo(CertificateActionType.CREATE, Optional.of(actionEvaluation))) {
      throw new CertificateActionForbidden(
          "Not allowed to create certificate for %s".formatted(certificateModelId),
          certificateModel.reasonNotAllowed(CertificateActionType.CREATE,
              Optional.of(actionEvaluation))
      );
    }

    if (unitDontHaveAccess(certificateModel.id().type(), actionEvaluation)) {
      throw new CertificateActionForbidden(
          "Not allowed to create certificate for %s since feature is not active for certificate type %s".formatted(
              certificateModelId, certificateModel.id().type()),
          List.of(
              "Åtgärden kan inte genomföras eftersom den inte är tillgänglig för vårdgivare/vårdenhet eller mottagningen"
          )
      );
    }

    final var certificate = certificateRepository.create(certificateModel);
    certificate.updateMetadata(actionEvaluation);
    certificate.externalReference(externalReference);
    certificate.prefill(prefillXml, prefillProcessor);

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

  private boolean unitDontHaveAccess(CertificateType certificateType,
      ActionEvaluation actionEvaluation) {
    final var actionRuleUnitAccess = new ActionRuleCertificateTypeActiveForUnit(
        certificateActionConfigurationRepository
    );

    return !actionRuleUnitAccess.evaluate(certificateType, actionEvaluation);
  }
}