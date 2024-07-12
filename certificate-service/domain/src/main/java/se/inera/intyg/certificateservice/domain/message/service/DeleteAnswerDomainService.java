package se.inera.intyg.certificateservice.domain.message.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.repository.MessageRepository;

@RequiredArgsConstructor
public class DeleteAnswerDomainService {

  private final MessageRepository messageRepository;

  public Message delete(Message message, Certificate certificate,
      ActionEvaluation actionEvaluation) {
    if (!certificate.allowTo(CertificateActionType.DELETE_ANSWER,
        Optional.of(actionEvaluation))) {
      throw new CertificateActionForbidden(
          "Not allowed to delete answer on certificate for %s"
              .formatted(certificate.id().id()),
          certificate.reasonNotAllowed(CertificateActionType.DELETE_ANSWER, Optional.empty())
      );
    }

    message.deleteAnswer();

    return messageRepository.save(message);
  }
}
