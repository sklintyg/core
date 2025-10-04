package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper;

import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.PseudonymizedAnalyticsMessage;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.AdministrativeMessageEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.AdministrativeMessageRecipientRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.AdministrativeMessageSenderRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.AdministrativeMessageTypeRepository;

@Component
@RequiredArgsConstructor
public class AdministrativeMessageEntityMapper {

  private final AdministrativeMessageTypeRepository administrativeMessageTypeRepository;
  private final AdministrativeMessageSenderRepository administrativeMessageSenderRepository;
  private final AdministrativeMessageRecipientRepository administrativeMessageRecipientRepository;

  public AdministrativeMessageEntity map(PseudonymizedAnalyticsMessage message) {
    return AdministrativeMessageEntity.builder()
        .administrativeMessageId(message.getMessageId())
        .messageType(administrativeMessageTypeRepository.findOrCreate(
            message.getMessageType()))
        .sent(message.getMessageSent())
        .lastDateToAnswer(message.getMessageLastDateToAnswer())
        .questionId(message.getMessageQuestionIds())
        .sender(administrativeMessageSenderRepository.findOrCreate(
            message.getMessageSenderId()))
        .recipient(administrativeMessageRecipientRepository.findOrCreate(
            message.getMessageRecipientId()))
        .relations(Collections.emptyList())
        .build();
  }
}
