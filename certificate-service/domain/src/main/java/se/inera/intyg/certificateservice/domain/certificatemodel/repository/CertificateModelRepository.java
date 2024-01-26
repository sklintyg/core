package se.inera.intyg.certificateservice.domain.certificatemodel.repository;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;

public interface CertificateModelRepository {

  public List<CertificateModel> findAllActive();
}
