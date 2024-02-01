package se.inera.intyg.certificateservice.infrastructure.certificatemodel.persistence;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;

@Repository
@RequiredArgsConstructor
public class InMemoryCertificateRepository implements CertificateRepository {

  private final Map<CertificateId, Certificate> certificateMap = new HashMap<>();

  @Override
  public Certificate create(CertificateModel certificateModel) {
    if (certificateModel == null) {
      throw new IllegalArgumentException("Unable to create, certificateModel was null.");
    }
    return Certificate.builder()
        .id(new CertificateId(UUID.randomUUID().toString()))
        .created(LocalDateTime.now(ZoneId.systemDefault()))
        .certificateModel(certificateModel)
        .build();
  }

  @Override
  public Certificate save(Certificate certificate) {
    if (certificate == null) {
      throw new IllegalArgumentException(
          "Unable to save, certificate was null"
      );
    }
    certificateMap.put(certificate.id(), certificate);
    return certificate;
  }

  @Override
  public Certificate getById(CertificateId certificateId) {
    if (!certificateMap.containsKey(certificateId)) {
      throw new IllegalStateException(
          "CertificateId '%s' not present in repository".formatted(certificateId)
      );
    }
    return certificateMap.get(certificateId);
  }
}
