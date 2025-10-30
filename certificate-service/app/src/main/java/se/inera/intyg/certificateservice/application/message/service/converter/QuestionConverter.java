package se.inera.intyg.certificateservice.application.message.service.converter;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.message.dto.AnswerDTO;
import se.inera.intyg.certificateservice.application.message.dto.QuestionDTO;
import se.inera.intyg.certificateservice.application.message.dto.QuestionTypeDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.RelationType;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.message.model.Answer;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.model.MessageActionLink;
import se.inera.intyg.certificateservice.domain.message.model.MessageStatus;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;

@Component
@RequiredArgsConstructor
public class QuestionConverter {

  private final ReminderConverter reminderConverter;
  private final ComplementConverter complementConverter;
  private final CertificateRepository certificateRepository;
  private final CertificateRelationConverter certificateRelationConverter;
  private final MessageActionLinkConverter messageActionLinkConverter;

  public QuestionDTO convert(Message message, List<MessageActionLink> messageActionLinks) {
    final var certificate = certificateRepository.getById(message.certificateId());
    return QuestionDTO.builder()
        .id(message.id().id())
        .certificateId(message.certificateId().id())
        .type(QuestionTypeDTO.valueOf(message.type().name()))
        .author(
            message.authoredStaff() != null
                ? message.authoredStaff().name().fullName()
                : message.author().name()
        )
        .subject(
            message.authoredStaff() != null
                ? message.subject().subject()
                : message.type().displayName() + " - " + message.subject().subject()
        )
        .sent(message.sent())
        .isHandled(message.status().equals(MessageStatus.HANDLED))
        .isForwarded(message.forwarded().value())
        .message(message.content().content())
        .lastUpdate(message.modified())
        .lastDateToReply(message.lastDateToReply())
        .answeredByCertificate(
            message.type().equals(MessageType.COMPLEMENT)
                ? certificateRelationConverter.convert(
                certificate.latestChildRelation(
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
            messageActionLinks.stream()
                .map(messageActionLinkConverter::convert)
                .toList()
        )
        .answer(
            message.answer() != null
                ? AnswerDTO.builder()
                .id(message.answer().id().id())
                .author(getAuthor(message.answer()))
                .sent(message.answer().sent())
                .message(message.answer().content().content())
                .contactInfo(getContactInfo(message))
                .build()
                : null
        )
        .build();
  }

  private static String getAuthor(Answer answer) {
    return answer.authoredStaff() != null
        ? answer.authoredStaff().name().fullName()
        : answer.author().name();
  }

  private static List<String> getContactInfo(Message message) {
    return
        message.answer().contactInfo() != null && !message.answer().contactInfo().lines().isEmpty()
            ? message.answer().contactInfo().lines()
            : null;
  }

}
