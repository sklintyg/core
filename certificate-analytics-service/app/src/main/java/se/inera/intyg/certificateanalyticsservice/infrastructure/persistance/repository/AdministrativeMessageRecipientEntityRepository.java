package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.AdministrativeMessageRecipientEntity;

@Repository
public interface AdministrativeMessageRecipientEntityRepository extends
    CrudRepository<AdministrativeMessageRecipientEntity, Byte> {

  Optional<AdministrativeMessageRecipientEntity> findByRecipient(String recipient);
}
