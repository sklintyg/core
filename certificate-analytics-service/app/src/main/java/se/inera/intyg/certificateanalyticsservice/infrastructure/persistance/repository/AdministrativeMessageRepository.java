package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.PseudonymizedAnalyticsMessage;
import se.inera.intyg.certificateanalyticsservice.domain.AdministrativeMessageRelationType;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.AdministrativeMessageEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.AdministrativeMessageRelationEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.AdministrativeMessageEntityMapper;

@Repository
@RequiredArgsConstructor
public class AdministrativeMessageRepository {

  private final AdministrativeMessageEntityRepository administrativeMessageEntityRepository;
  private final AdministrativeMessageEntityMapper administrativeMessageEntityMapper;
  private final AdministrativeMessageRelationEntityRepository administrativeMessageRelationEntityRepository;
  private final AdministrativeMessageRelationTypeRepository administrativeMessageRelationTypeRepository;

  public AdministrativeMessageEntity findOrCreate(PseudonymizedAnalyticsMessage message) {
    if (message.getMessageId() == null || message.getMessageId()
        .isBlank()) {
      return null;
    }

    final var messageEntity = administrativeMessageEntityRepository.findByAdministrativeMessageId(
            message.getMessageId())
        .orElseGet(() -> administrativeMessageEntityRepository.save(
            administrativeMessageEntityMapper.map(message))
        );

    handleRelations(message, messageEntity);

    return messageEntity;
  }

  private void handleRelations(PseudonymizedAnalyticsMessage message,
      AdministrativeMessageEntity messageEntity) {
    if (message.getMessageAnswerId() == null
        && message.getMessageReminderId() == null) {
      return;
    }

    final var existing = administrativeMessageRelationEntityRepository.findByRelationId(
        message.getMessageAnswerId());
    if (existing.isEmpty()) {
      administrativeMessageRelationEntityRepository.save(
          getRelation(message, messageEntity)
      );
    }
  }

  private AdministrativeMessageRelationEntity getRelation(PseudonymizedAnalyticsMessage message,
      AdministrativeMessageEntity messageEntity) {
    final var type = message.getMessageAnswerId() != null ?
        AdministrativeMessageRelationType.ANSWER
        : AdministrativeMessageRelationType.REMINDER;

    final var id = message.getMessageAnswerId() != null ?
        message.getMessageAnswerId()
        : message.getMessageReminderId();

    return AdministrativeMessageRelationEntity.builder()
        .relationId(id)
        .relationType(administrativeMessageRelationTypeRepository.findOrCreate(
            type))
        .administrativeMessage(messageEntity)
        .build();
  }
}
