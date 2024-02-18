package se.inera.intyg.certificateservice.domain.certificate.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;

@RequiredArgsConstructor
public class UpdateCertificateDomainService {

  private final CertificateRepository certificateRepository;

  public Certificate update(CertificateId certificateId, List<ElementData> elementData,
      ActionEvaluation actionEvaluation) {
    final var certificate = certificateRepository.getById(certificateId);
    if (!certificate.allowTo(CertificateActionType.UPDATE, actionEvaluation)) {
      throw new CertificateActionForbidden(
          "Not allowed to update certificate for %s".formatted(certificateId)
      );
    }

    certificate.updateMetadata(actionEvaluation);
    certificate.updateData(elementData);

    return certificateRepository.save(certificate);
  }
}
