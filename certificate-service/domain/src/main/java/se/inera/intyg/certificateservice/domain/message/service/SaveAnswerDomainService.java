package se.inera.intyg.certificateservice.domain.message.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.message.model.Content;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.repository.MessageRepository;
import se.inera.intyg.certificateservice.domain.staff.model.Staff;

@RequiredArgsConstructor
public class SaveAnswerDomainService {

  private final MessageRepository messageRepository;

  public Message save(Message message, Certificate certificate,
      ActionEvaluation actionEvaluation, Content content) {
    if (!certificate.allowTo(CertificateActionType.SAVE_ANSWER,
        Optional.of(actionEvaluation))) {
      throw new CertificateActionForbidden(
          "Not allowed to save answer on certificate for %s"
              .formatted(certificate.id().id()),
          certificate.reasonNotAllowed(CertificateActionType.SAVE_ANSWER, Optional.empty())
      );
    }

    message.saveAnswer(Staff.create(actionEvaluation.user()), content);

    return messageRepository.save(message);
  }
}
