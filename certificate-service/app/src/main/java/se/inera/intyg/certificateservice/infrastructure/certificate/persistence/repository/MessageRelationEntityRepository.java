package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageRelationEntity;

@Repository
public interface MessageRelationEntityRepository extends
    CrudRepository<MessageRelationEntity, Long> {

  List<MessageRelationEntity> findByParentMessage(
      MessageEntity messageEntity);

  Optional<MessageRelationEntity> findByChildMessage(MessageEntity messageEntity);

  void deleteAllByChildMessageOrParentMessage(
      MessageEntity messageEntityParent, MessageEntity messageEntityChild);
}