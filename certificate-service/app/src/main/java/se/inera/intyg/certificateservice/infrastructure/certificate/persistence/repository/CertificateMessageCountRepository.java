package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CertificateMessageCountRepository extends
    CrudRepository<CertificateMessageCountEntity, Long> {

  @Query(nativeQuery = true)
  Optional<List<CertificateMessageCountEntity>> getMessageCountForCertificates(
      List<String> patientIds,
      Integer maxDaysOfUnansweredCommunication);
}

