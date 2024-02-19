package se.inera.intyg.certificateservice.domain.certificate.service;

import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateModelRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;

@RequiredArgsConstructor
public class CreateCertificateDomainService {

  private final CertificateModelRepository certificateModelRepository;
  private final CertificateRepository certificateRepository;

  public Certificate create(CertificateModelId certificateModelId,
      ActionEvaluation actionEvaluation) {
    final var certificateModel = certificateModelRepository.getById(certificateModelId);
    if (!certificateModel.allowTo(CertificateActionType.CREATE, actionEvaluation)) {
      throw new CertificateActionForbidden(
          "Not allowed to create certificate for %s".formatted(certificateModelId)
      );
    }

    final var certificate = certificateRepository.create(certificateModel);
    certificate.updateMetadata(actionEvaluation);

    return certificateRepository.save(certificate);
  }
}
