package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.domain.common.model.MessagesRequest;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.model.MessageId;
import se.inera.intyg.certificateservice.domain.message.model.MessageStatus;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.MessageEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.MessageEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.MessageEntitySpecificationFactory;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.MessageRelationEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.MessageRelationRepository;
import se.inera.intyg.certificateservice.testability.certificate.service.repository.TestabilityMessageRepository;

@Repository
@RequiredArgsConstructor
public class JpaMessageRepository implements TestabilityMessageRepository {

  private final MessageEntityRepository messageEntityRepository;
  private final MessageEntityMapper messageEntityMapper;
  private final MessageRelationRepository messageRelationRepository;
  private final MessageRelationEntityRepository messageRelationEntityRepository;
  private final MessageEntitySpecificationFactory messageEntitySpecificationFactory;


  @Override
  public Message save(Message message) {
    if (message == null) {
      throw new IllegalArgumentException(
          "Unable to save, message was null"
      );
    }

    if (message.status() == MessageStatus.DELETED_DRAFT) {
      messageEntityRepository.findMessageEntityById(message.id().id())
          .ifPresent(messageEntityRepository::delete);
      return message;
    }

    final var savedEntity = messageEntityRepository.save(
        messageEntityMapper.toEntity(
            message,
            messageEntityRepository.findMessageEntityById(message.id().id())
                .map(MessageEntity::getKey)
                .orElse(null)
        )
    );

    messageRelationRepository.save(message, savedEntity);

    return messageEntityMapper.toDomain(savedEntity);
  }

  @Override
  public void remove(List<String> messageIds) {
    if (messageIds.isEmpty()) {
      return;
    }

    messageIds.stream()
        .map(messageEntityRepository::findMessageEntityById)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .forEach(
            entity ->
                messageRelationEntityRepository.deleteAllByChildMessageOrParentMessage(
                    entity,
                    entity)
        );

    messageEntityRepository.deleteAllByIdIn(messageIds);
  }

  @Override
  public boolean exists(MessageId messageId) {
    return messageEntityRepository.findMessageEntityById(messageId.id()).isPresent();
  }

  @Override
  public Message getById(MessageId messageId) {
    if (messageId == null) {
      throw new IllegalArgumentException("Cannot get message if messageId is null");
    }
    final var messageEntity = messageEntityRepository.findMessageEntityById(
            messageId.id()
        )
        .orElseThrow(() ->
            new IllegalArgumentException(
                "MessgeId '%s' not present in repository".formatted(messageId)
            )
        );

    return messageEntityMapper.toDomain(messageEntity);
  }

  @Override
  public Message findById(MessageId messageId) {
    if (messageId == null) {
      throw new IllegalArgumentException("Cannot get message if messageId is null");

    }
    final var messageEntity = messageEntityRepository.findMessageEntityById(
            messageId.id()
        )
        .orElseThrow(() ->
            new IllegalArgumentException(
                "MessgeId '%s' not present in repository".formatted(messageId)
            )
        );

    return messageRelationEntityRepository.findByChildMessage(messageEntity)
        .map(relation -> messageEntityMapper.toDomain(relation.getParentMessage()))
        .orElse(messageEntityMapper.toDomain(messageEntity));
  }

  @Override
  public List<Message> findByMessagesRequest(MessagesRequest request) {
    final var specification = messageEntitySpecificationFactory.create(request);

    return messageEntityRepository.findAll(specification).stream()
        .map(messageEntityMapper::toDomain)
        .filter(message -> !messageIsValidType(message))
        .toList();
  }

  private boolean messageIsValidType(Message message) {
    return message.type() == MessageType.ANSWER || message.type() == MessageType.REMINDER
        || message.type() == MessageType.MISSING || message.status() == MessageStatus.DRAFT;
  }
}