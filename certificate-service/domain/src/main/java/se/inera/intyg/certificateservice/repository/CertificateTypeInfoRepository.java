package se.inera.intyg.certificateservice.repository;

import java.util.List;
import se.inera.intyg.certificateservice.model.CertificateTypeInfo;

public interface CertificateTypeInfoRepository {

  public List<CertificateTypeInfo> get();
}
