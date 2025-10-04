package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.MessageTypeEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.MessageTypeEntityMapper;

@Repository
@RequiredArgsConstructor
public class MessageTypeRepository {

  private final MessageTypeEntityRepository messageTypeEntityRepository;

  public MessageTypeEntity findOrCreate(String messageType) {
    return messageTypeEntityRepository.findByType(messageType)
        .orElseGet(() ->
            messageTypeEntityRepository.save(MessageTypeEntityMapper.map(messageType))
        );
  }
}
