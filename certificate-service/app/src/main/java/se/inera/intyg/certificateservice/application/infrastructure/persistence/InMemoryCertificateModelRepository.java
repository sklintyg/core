package se.inera.intyg.certificateservice.application.infrastructure.persistence;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.model.CertificateModel;
import se.inera.intyg.certificateservice.repository.CertificateModelRepository;

@Repository
public class InMemoryCertificateModelRepository implements CertificateModelRepository {

  @Override
  public List<CertificateModel> findAllActive() {
    return Collections.emptyList();
  }
}
