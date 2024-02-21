package se.inera.intyg.certificateservice.domain.certificate.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.validation.model.ValidationResult;

@RequiredArgsConstructor
public class ValidateCertificateDomainService {

  private final CertificateRepository certificateRepository;

  public ValidationResult validate(CertificateId certificateId, List<ElementData> elementData,
      ActionEvaluation actionEvaluation) {
    final var certificate = certificateRepository.getById(certificateId);
    if (!certificate.allowTo(CertificateActionType.READ, actionEvaluation)) {
      throw new CertificateActionForbidden(
          "Not allowed to validate certificate for %s".formatted(certificateId)
      );
    }

    return certificate.validate(elementData);
  }
}
