package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.PseudonymizedAnalyticsMessage;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.MessageEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.MessageEntityRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.MessageTypeRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.PartyRepository;

@Component
@RequiredArgsConstructor
public class MessageEntityMapper {

  private final MessageEntityRepository messageEntityRepository;
  private final MessageTypeRepository messageTypeRepository;
  private final PartyRepository partyRepository;

  public MessageEntity map(PseudonymizedAnalyticsMessage pseudonymizedAnalyticsMessage) {
    if (missingMessage(pseudonymizedAnalyticsMessage)) {
      return null;
    }

    return messageEntityRepository.findByMessageId(pseudonymizedAnalyticsMessage.getMessageId())
        .orElseGet(() -> createAndSave(pseudonymizedAnalyticsMessage));
  }

  private static boolean missingMessage(
      PseudonymizedAnalyticsMessage pseudonymizedAnalyticsMessage) {
    return pseudonymizedAnalyticsMessage.getMessageId() == null
        || pseudonymizedAnalyticsMessage.getMessageId().isBlank();
  }

  private MessageEntity createAndSave(PseudonymizedAnalyticsMessage pseudonymizedAnalyticsMessage) {
    return messageEntityRepository.save(
        MessageEntity.builder()
            .messageId(pseudonymizedAnalyticsMessage.getMessageId())
            .messageType(
                messageTypeRepository.findOrCreate(pseudonymizedAnalyticsMessage.getMessageType())
            )
            .messageAnswerId(pseudonymizedAnalyticsMessage.getMessageAnswerId())
            .messageReminderId(pseudonymizedAnalyticsMessage.getMessageReminderId())
            .sent(pseudonymizedAnalyticsMessage.getMessageSent())
            .lastDateToAnswer(pseudonymizedAnalyticsMessage.getMessageLastDateToAnswer())
            .sender(
                partyRepository.findOrCreate(pseudonymizedAnalyticsMessage.getMessageSenderId())
            )
            .recipient(
                partyRepository.findOrCreate(pseudonymizedAnalyticsMessage.getMessageRecipientId())
            )
            .questionIds(pseudonymizedAnalyticsMessage.getMessageQuestionIds())
            .build()
    );
  }
}
