package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageEntity;

@Repository
public interface MessageEntityRepository extends CrudRepository<MessageEntity, Long>,
    JpaSpecificationExecutor<MessageEntity> {

  List<MessageEntity> findMessageEntitiesByCertificate(CertificateEntity certificate);

  Optional<MessageEntity> findMessageEntityById(String id);

  void deleteAllByIdIn(List<String> messageIds);

  @Query(value = "SELECT m.* FROM message m INNER JOIN message_status ms ON m.message_status_key = ms.key WHERE m.certificate_key = :certificateKey AND ms.status = 'SENT' AND m.author = 'FK' AND m.created >= DATE_SUB(NOW(), INTERVAL :maxDaysOfUnansweredCommunication DAY)", nativeQuery = true)
  List<MessageEntity> findMessageEntitiesByCertificate_KeyAndCreatedAfter(
      @Param("certificateKey") Long certificateKey,
      @Param("maxDaysOfUnansweredCommunication") Integer maxDaysOfUnansweredCommunication);
}
