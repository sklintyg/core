package se.inera.intyg.certificateservice.domain.certificatemodel.repository;

import java.util.List;
import java.util.Optional;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;

public interface CertificateModelRepository {

  public List<CertificateModel> findAllActive();

  Optional<CertificateModel> findLatestActiveByType(CertificateType certificateType);
}
