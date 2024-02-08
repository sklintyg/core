package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import static se.inera.intyg.certificateservice.testability.common.TestabilityConstants.TESTABILITY_PROFILE;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.testability.certificate.service.repository.TestabilityCertificateRepository;

@Profile("!" + TESTABILITY_PROFILE)
@Repository
@RequiredArgsConstructor
public class TestabilityEnabledInMemoryCertificateRepository
    extends InMemoryCertificateRepository
    implements TestabilityCertificateRepository {

  @Override
  public Certificate insert(Certificate certificate) {
    return save(certificate);
  }

  @Override
  public void remove(List<CertificateId> certificateIds) {
    certificateIds.forEach(
        certificateId -> certificateMap.remove(certificateId, certificateMap.get(certificateId))
    );
  }
}
