package se.inera.intyg.certificateservice.domain.message.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.message.model.Message;

@RequiredArgsConstructor
public class GetCertificateMessageDomainService {

  private final CertificateRepository certificateRepository;

  public List<Message> get(ActionEvaluation actionEvaluation, CertificateId certificateId) {
    final var certificate = certificateRepository.getById(certificateId);
    if (!certificate.allowTo(CertificateActionType.READ, Optional.of(actionEvaluation))) {
      throw new CertificateActionForbidden(
          "Not allowed to read certificate for %s".formatted(certificateId),
          certificate.reasonNotAllowed(CertificateActionType.READ, Optional.of(actionEvaluation))
      );
    }

    certificate.messages()
        .forEach(message -> message.evaluateAvailableActions(actionEvaluation, certificate));

    return certificate.messages();
  }
}
