package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.MessageEntity;

@Repository
public interface MessageEntityRepository extends CrudRepository<MessageEntity, Long> {

  List<MessageEntity> findMessageEntitiesByCertificate(CertificateEntity certificate);
}
