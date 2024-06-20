package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
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

}
