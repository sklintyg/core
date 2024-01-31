package se.inera.intyg.certificateservice.infrastructure.certificatemodel.persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;

@Slf4j
@Repository
@RequiredArgsConstructor
public class InMemoryCertificateRepository implements CertificateRepository {

  private Map<CertificateId, Certificate> certificateMap;

  @Override
  public Certificate create(CertificateModel certificateModel) {
    return Certificate.builder()
        .id(new CertificateId(UUID.randomUUID().toString()))
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
    saveCertificate(certificate);
    return certificate;
  }

  @Override
  public Certificate get(CertificateId certificateId) {
    initializeRepository();
    return certificateMap.get(certificateId);
  }

  private void saveCertificate(Certificate certificate) {
    initializeRepository();
    if (certificateMap.containsKey(certificate.getId())) {
      throw new IllegalStateException(
          "Certificate already present in repository with id '%s'".formatted(certificate.getId())
      );
    }

    certificateMap.put(certificate.getId(), certificate);
  }

  private void initializeRepository() {
    if (certificateMap == null) {
      log.info("Initiate certificate repository");
      certificateMap = new HashMap<>();
    }
  }
}
