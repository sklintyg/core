package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.AdministrativeMessageSenderEntity;

@Repository
public interface AdministrativeMessageSenderEntityRepository extends
    CrudRepository<AdministrativeMessageSenderEntity, Byte> {

  Optional<AdministrativeMessageSenderEntity> findBySender(String sender);
}
