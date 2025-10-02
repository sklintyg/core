package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.RecipientEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.RecipientEntityMapper;

@Repository
@RequiredArgsConstructor
public class RecipientRepository {

  private final RecipientEntityRepository recipientEntityRepository;

  public RecipientEntity findOrCreate(String recipient) {
    if (recipient == null || recipient.isBlank()) {
      return null;
    }
    return recipientEntityRepository.findByRecipient(recipient)
        .orElseGet(() -> recipientEntityRepository.save(RecipientEntityMapper.map(recipient)));
  }
}
