package se.inera.intyg.certificateservice.domain.unit.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.common.model.MessagesRequest;
import se.inera.intyg.certificateservice.domain.common.model.MessagesResponse;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
import se.inera.intyg.certificateservice.domain.message.repository.MessageRepository;

@RequiredArgsConstructor
public class GetUnitMessagesDomainService {

  private final MessageRepository messageRepository;
  private final CertificateRepository certificateRepository;

  public MessagesResponse get(MessagesRequest request, ActionEvaluation actionEvaluation) {
    final var messages = messageRepository.findByMessagesRequest(request)
        .stream()
        .filter(message -> !isReminderOrAnswer(message))
        .toList();

    final var certificates = messages.stream()
        .map(Message::certificateId)
        .distinct()
        .map(certificateRepository::getById)
        .filter(certificate -> certificate.allowTo(
                CertificateActionType.READ,
                Optional.of(actionEvaluation)
            )
        ).toList();

    final var filteredMessagesBasedOnActionEvaluation = messages.stream()
        .filter(message -> certificates.stream()
            .anyMatch(certificate -> certificate.id().id().equals(message.certificateId().id()))
        ).toList();

    return MessagesResponse.builder()
        .messages(filteredMessagesBasedOnActionEvaluation)
        .certificates(certificates)
        .build();
  }

  private static boolean isReminderOrAnswer(Message message) {
    return message.type() == MessageType.ANSWER || message.type() == MessageType.REMINDER;
  }
}
