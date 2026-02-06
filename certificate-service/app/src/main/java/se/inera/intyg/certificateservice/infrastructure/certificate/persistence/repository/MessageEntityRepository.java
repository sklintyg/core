package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository;

import java.time.LocalDateTime;
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

  @Query("SELECT m FROM MessageEntity m WHERE m.certificate.key = :certificateKey AND m.status.status IN ('SENT') AND m.author = 'FK' AND m.created >= :createdAfter")
  List<MessageEntity> findMessageEntitiesByCertificate_KeyAndCreatedAfter(
      @Param("certificateKey") Long certificateKey,
      @Param("createdAfter") LocalDateTime createdAfter);
}
