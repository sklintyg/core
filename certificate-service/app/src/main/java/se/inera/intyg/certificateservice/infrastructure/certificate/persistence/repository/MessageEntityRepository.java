package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageEntity;

@Repository
public interface MessageEntityRepository extends CrudRepository<MessageEntity, Long>,
    JpaSpecificationExecutor<MessageEntity> {

  List<MessageEntity> findMessageEntitiesByCertificate(CertificateEntity certificate);

  Optional<MessageEntity> findMessageEntityById(String id);

  void deleteAllByIdIn(List<String> messageIds);

  @Query(value = "SELECT c.certificate_id AS certificateId, " +
      "SUM(CASE WHEN mt.type = 'COMPLEMENT' THEN 1 ELSE 0 END) AS complementsCount, " +
      "SUM(CASE WHEN mt.type IN ('OTHER', 'CONTACT', 'COORDINATION', 'ANSWER') THEN 1 ELSE 0 END) AS othersCount "
      +
      "FROM certificate c " +
      "JOIN message m ON m.certificate_key = c.key " +
      "JOIN message_type mt ON m.message_type_key = mt.key " +
      "JOIN patient p ON c.patient_key = p.key " +
      "JOIN message_status ms ON m.message_status_key = ms.key " +
      "WHERE p.patient_id IN :patientIds AND m.created >= DATE_SUB(NOW(), INTERVAL :maxDays DAY) AND m.author = 'FK' AND ms.status = 'SENT' "
      +
      "GROUP BY c.certificate_id", nativeQuery = true)
  List<CertificateMessageCountEntity> getMessageCountForCertificates(
      List<String> patientIds,
      int maxDays);
}
