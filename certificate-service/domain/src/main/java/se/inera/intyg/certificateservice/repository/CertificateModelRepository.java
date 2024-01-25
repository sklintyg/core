package se.inera.intyg.certificateservice.repository;

import java.util.List;
import se.inera.intyg.certificateservice.model.CertificateModel;

public interface CertificateModelRepository {

  public List<CertificateModel> findAllActive();
}
