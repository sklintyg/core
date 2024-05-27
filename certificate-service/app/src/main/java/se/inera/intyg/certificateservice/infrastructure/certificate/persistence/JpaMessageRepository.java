package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.model.MessageId;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.MessageEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.MessageEntityRepository;
import se.inera.intyg.certificateservice.testability.certificate.service.repository.TestabilityMessageRepository;

@Repository
@RequiredArgsConstructor
public class JpaMessageRepository implements TestabilityMessageRepository {

  private final MessageEntityRepository messageEntityRepository;
  private final MessageEntityMapper messageEntityMapper;

  @Override
  public Message save(Message message) {
    if (message == null) {
      throw new IllegalArgumentException(
          "Unable to save, message was null"
      );
    }

    final var savedEntity = messageEntityRepository.save(
        messageEntityMapper.toEntity(
            message,
            messageEntityRepository.findMessageEntityById(message.id().id())
                .map(MessageEntity::getKey)
                .orElse(null)
        )
    );

    return messageEntityMapper.toDomain(savedEntity);
  }

  @Override
  public void remove(List<String> messageIds) {
    if (messageIds.isEmpty()) {
      return;
    }

    messageEntityRepository.deleteAllByIdIn(messageIds);
  }

  @Override
  public boolean exists(MessageId messageId) {
    return messageEntityRepository.findMessageEntityById(messageId.id()).isPresent();
  }
}
