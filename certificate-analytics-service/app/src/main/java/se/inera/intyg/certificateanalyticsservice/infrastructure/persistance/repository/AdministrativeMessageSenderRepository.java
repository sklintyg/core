package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.AdministrativeMessageSenderEntity;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.AdministrativeMessageSenderEntityMapper;

@Repository
@RequiredArgsConstructor
public class AdministrativeMessageSenderRepository {

  private final AdministrativeMessageSenderEntityRepository administrativeMessageSenderEntityRepository;

  public AdministrativeMessageSenderEntity findOrCreate(String sender) {
    if (sender == null || sender.isBlank()) {
      return null;
    }
    return administrativeMessageSenderEntityRepository.findBySender(sender)
        .orElseGet(() -> administrativeMessageSenderEntityRepository.save(
            AdministrativeMessageSenderEntityMapper.map(sender)));
  }
}
