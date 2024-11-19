package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository;

import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageRelationType.ANSWER;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.model.MessageStatus;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageRelationEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageRelationTypeEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.AnswerToMessageEntityMapper;

@Component
@RequiredArgsConstructor
public class MessageRelationAnswer implements MessageRelation {

  private final MessageRelationEntityRepository messageRelationEntityRepository;
  private final MessageEntityRepository messageEntityRepository;
  private final AnswerToMessageEntityMapper answerToMessageEntityMapper;

  @Override
  public void save(Message message, MessageEntity messageEntity) {
    if (message.answer() == null) {
      return;
    }

    if (message.answer().status() == MessageStatus.DELETED_DRAFT) {
      deleteMessageAndRelations(message, messageEntity);
      return;
    }

    final var savedAnswer = messageEntityRepository.save(
        answerToMessageEntityMapper.toEntity(
            messageEntity,
            message.answer(),
            messageEntityRepository.findMessageEntityById(message.answer().id().id())
                .map(MessageEntity::getKey)
                .orElse(null))
    );

    final var messageRelations = messageRelationEntityRepository.findByParentMessage(
        messageEntity
    );

    if (messageRelations.isEmpty() || isMissingAnswer(messageRelations)) {
      messageRelationEntityRepository.save(
          MessageRelationEntity.builder()
              .childMessage(savedAnswer)
              .parentMessage(messageEntity)
              .messageRelationType(
                  MessageRelationTypeEntity.builder()
                      .key(ANSWER.getKey())
                      .type(ANSWER.name())
                      .build()
              )
              .build()
      );
    }
  }

  private static boolean isMissingAnswer(List<MessageRelationEntity> messageRelations) {
    return messageRelations.stream().noneMatch(
        messageRelationEntity -> messageRelationEntity.getMessageRelationType().getType()
            .equals(ANSWER.name())
    );
  }

  private void deleteMessageAndRelations(Message message, MessageEntity messageEntity) {
    messageRelationEntityRepository.findByParentMessage(messageEntity).stream()
        .filter(entity -> entity.getChildMessage().getId().equals(message.answer().id().id()))
        .findFirst()
        .ifPresent(messageRelationEntityRepository::delete);

    messageEntityRepository.findMessageEntityById(message.answer().id().id())
        .ifPresent(messageEntityRepository::delete);
  }
}

