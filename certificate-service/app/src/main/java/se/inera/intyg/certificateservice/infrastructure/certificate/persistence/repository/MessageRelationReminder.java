package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository;

import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageRelationType.REMINDER;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageRelationEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageRelationTypeEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.ReminderToMessageEntityMapper;

@Component
@RequiredArgsConstructor
public class MessageRelationReminder implements MessageRelation {

  private final MessageRelationEntityRepository messageRelationEntityRepository;
  private final MessageEntityRepository messageEntityRepository;
  private final ReminderToMessageEntityMapper reminderToMessageEntityMapper;

  @Override
  public void save(Message message, MessageEntity messageEntity) {
    if (message.reminders().isEmpty()) {
      return;
    }

    message.reminders().forEach(
        reminder -> {
          final var entity = messageEntityRepository.findMessageEntityById(reminder.id().id());
          if (entity.isEmpty()) {
            final var savedReminder = messageEntityRepository.save(
                reminderToMessageEntityMapper.toEntity(messageEntity, reminder)
            );

            messageRelationEntityRepository.save(
                MessageRelationEntity.builder()
                    .childMessage(savedReminder)
                    .parentMessage(messageEntity)
                    .messageRelationType(
                        MessageRelationTypeEntity.builder()
                            .key(REMINDER.getKey())
                            .type(REMINDER.name())
                            .build()
                    )
                    .build()
            );
          }
        }
    );
  }
}

