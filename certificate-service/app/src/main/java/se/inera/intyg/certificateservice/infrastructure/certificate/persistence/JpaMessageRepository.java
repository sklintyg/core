package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.repository.MessageRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.MessageEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.MessageEntityRepository;

@Repository
@RequiredArgsConstructor
public class JpaMessageRepository implements MessageRepository {

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
        messageEntityMapper.toEntity(message)
    );

    return messageEntityMapper.toDomain(savedEntity);
  }
}