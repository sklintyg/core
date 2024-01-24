package se.inera.intyg.certificateservice.application.infrastructure.persistence;

import java.util.List;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.model.CertificateTypeInfo;
import se.inera.intyg.certificateservice.repository.CertificateTypeInfoRepository;

@Repository
public class JpaCertificateTypeInfoRepository implements CertificateTypeInfoRepository {

  @Override
  public List<CertificateTypeInfo> get() {
    return null;
  }
}
