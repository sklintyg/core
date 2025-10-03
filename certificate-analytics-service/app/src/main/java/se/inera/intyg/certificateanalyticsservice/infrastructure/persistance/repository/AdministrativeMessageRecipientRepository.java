package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.AdministrativeMessageRecipientEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.AdministrativeMessageRecipientEntityMapper;

@Repository
@RequiredArgsConstructor
public class AdministrativeMessageRecipientRepository {

  private final AdministrativeMessageRecipientEntityRepository administrativeMessageRecipientEntityRepository;

  public AdministrativeMessageRecipientEntity findOrCreate(String recipient) {
    if (recipient == null || recipient.isBlank()) {
      return null;
    }
    return administrativeMessageRecipientEntityRepository.findByRecipient(recipient)
        .orElseGet(() -> administrativeMessageRecipientEntityRepository.save(
            AdministrativeMessageRecipientEntityMapper.map(recipient)));
  }
}
