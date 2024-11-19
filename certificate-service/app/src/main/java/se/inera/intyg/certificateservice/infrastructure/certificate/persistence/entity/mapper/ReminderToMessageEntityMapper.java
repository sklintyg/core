package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.message.model.Reminder;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageStatusEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageStatusEnum;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageTypeEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageTypeEnum;

@Component
@RequiredArgsConstructor
public class ReminderToMessageEntityMapper {

  public MessageEntity toEntity(MessageEntity messageEntity, Reminder reminder) {
    return MessageEntity.builder()
        .id(UUID.randomUUID().toString())
        .subject(reminder.subject().subject())
        .content(reminder.content().content())
        .author(reminder.author().author())
        .created(reminder.created() == null ? LocalDateTime.now(ZoneId.systemDefault())
            : reminder.created())
        .modified(reminder.created() == null ? LocalDateTime.now(ZoneId.systemDefault())
            : reminder.created())
        .sent(reminder.sent())
        .status(
            MessageStatusEntity.builder()
                .key(MessageStatusEnum.SENT.getKey())
                .status(MessageStatusEnum.SENT.name())
                .build()
        )
        .messageType(
            MessageTypeEntity.builder()
                .key(MessageTypeEnum.REMINDER.getKey())
                .type(MessageTypeEnum.REMINDER.name())
                .build()
        )
        .certificate(messageEntity.getCertificate())
        .forwarded(false)
        .build();
  }
}
