package se.inera.intyg.certificateservice.application.message.service.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.message.dto.QuestionDTO;
import se.inera.intyg.certificateservice.application.message.dto.QuestionTypeDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.RelationType;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.model.MessageStatus;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;

@Component
@RequiredArgsConstructor
public class QuestionConverter {

  private final ReminderConverter reminderConverter;
  private final ComplementConverter complementConverter;
  private final CertificateRepository certificateRepository;
  private final CertificateRelationConverter certificateRelationConverter;
  private final MessageActionConverter messageActionConverter;

  public QuestionDTO convert(Message message) {
    final var certificate = certificateRepository.getById(message.certificateId());
    return QuestionDTO.builder()
        .id(message.id().id())
        .certificateId(message.certificateId().id())
        .type(QuestionTypeDTO.COMPLEMENT)
        .author(message.author().author())
        .subject(message.subject().subject())
        .sent(message.sent())
        .isHandled(message.status().equals(MessageStatus.HANDLED))
        .isForwarded(message.forwarded().value())
        .message(message.content().content())
        .lastUpdate(message.modified())
        .lastDateToReply(message.lastDateToReply())
        .answeredByCertificate(
            message.type().equals(MessageType.COMPLEMENT)
                ? certificateRelationConverter.convert(
                certificate.getLatestChildRelationOfType(
                    RelationType.COMPLEMENT
                ))
                : null
        )
        .contactInfo(
            message.contactInfo().lines()
        )
        .reminders(
            message.reminders().stream()
                .map(reminderConverter::convert)
                .toList()
        )
        .complements(
            message.complements().stream()
                .map(complement -> complementConverter.convert(complement, certificate))
                .toList()
        )
        .links(
            message.availableActions().stream()
                .map(messageActionConverter::convert)
                .toList()
        )
        .build();
  }
}
